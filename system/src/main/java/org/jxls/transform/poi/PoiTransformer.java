package org.jxls.transform.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.*;
import org.jxls.transform.AbstractTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * POI implementation of {@link org.jxls.transform.Transformer} interface
 *
 * @author Leonid Vysochyn
 *         Date: 1/23/12
 */
public class PoiTransformer extends AbstractTransformer {
    private static final int MAX_COLUMN_TO_READ_COMMENT = 50;
    public static final String POI_CONTEXT_KEY = "util";

    private static Logger logger = LoggerFactory.getLogger(PoiTransformer.class);

    private Workbook workbook;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Integer lastCommentedColumn = MAX_COLUMN_TO_READ_COMMENT;
    private boolean isSXSSF = false;

    private PoiTransformer(Workbook workbook) {
        this.workbook = workbook;
    }

    public static PoiTransformer createTransformer(InputStream is, OutputStream os) throws IOException, InvalidFormatException {
        PoiTransformer transformer = createTransformer(is);
        transformer.outputStream = os;
        transformer.inputStream = is;
        return transformer;
    }

    public static PoiTransformer createTransformer(InputStream is) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(is);
        return createTransformer(workbook);
    }

    public static PoiTransformer createTransformer(Workbook workbook) {
        PoiTransformer transformer = new PoiTransformer(workbook);
        transformer.readCellData();
        return transformer;
    }

    public static PoiTransformer createSxssfTransformer(Workbook workbook) {
        return createSxssfTransformer(workbook, 100, false);
    }

    public static PoiTransformer createSxssfTransformer(Workbook workbook, int rowAccessWindowSize, boolean compressTmpFiles) {
        return createSxssfTransformer(workbook, rowAccessWindowSize, compressTmpFiles, false);
    }

    public static PoiTransformer createSxssfTransformer(Workbook workbook, int rowAccessWindowSize, boolean compressTmpFiles, boolean useSharedStringsTable) {
        PoiTransformer transformer = new PoiTransformer(workbook);
        transformer.isSXSSF = true;
        transformer.readCellData();
        if (workbook instanceof XSSFWorkbook) {
            transformer.workbook = new SXSSFWorkbook((XSSFWorkbook) workbook, rowAccessWindowSize, compressTmpFiles, useSharedStringsTable);
        } else {
            throw new IllegalArgumentException("Failed to create POI Transformer using SXSSF API as the input workbook is not XSSFWorkbook");
        }
        return transformer;
    }

    public static Context createInitialContext() {
        Context context = new Context();
        context.putVar(POI_CONTEXT_KEY, new PoiUtil());
        return context;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public Integer getLastCommentedColumn() {
        return lastCommentedColumn;
    }

    public void setLastCommentedColumn(Integer lastCommentedColumn) {
        this.lastCommentedColumn = lastCommentedColumn;
    }

    private void readCellData() {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            SheetData sheetData = PoiSheetData.createSheetData(sheet, this);
            sheetMap.put(sheetData.getSheetName(), sheetData);
        }
    }

    public void transform(CellRef srcCellRef, CellRef targetCellRef, Context context, boolean updateRowHeightFlag) {
        CellData cellData = this.getCellData(srcCellRef);
        if (cellData != null) {

            if (targetCellRef == null || targetCellRef.getSheetName() == null) {
                logger.info("Target cellRef is null or has empty sheet name, cellRef=" + targetCellRef);
                return;
            }
            Sheet destSheet = workbook.getSheet(targetCellRef.getSheetName());
            if (destSheet == null) {
                destSheet = workbook.createSheet(targetCellRef.getSheetName());
                PoiUtil.copySheetProperties(workbook.getSheet(srcCellRef.getSheetName()), destSheet);
            }
            SheetData sheetData = sheetMap.get(srcCellRef.getSheetName());
            if (!isIgnoreColumnProps()) {
                destSheet.setColumnWidth(targetCellRef.getCol(), sheetData.getColumnWidth(srcCellRef.getCol()));
            }
            Row destRow = destSheet.getRow(targetCellRef.getRow());
            if (destRow == null) {
                destRow = destSheet.createRow(targetCellRef.getRow());
            }
            if (updateRowHeightFlag && !isIgnoreRowProps()) {
                destSheet.getRow(targetCellRef.getRow()).setHeight((short) sheetData.getRowData(srcCellRef.getRow()).getHeight());
            }
            Cell destCell = destRow.getCell(targetCellRef.getCol());
            if (destCell == null) {
                destCell = destRow.createCell(targetCellRef.getCol());
            }
            try {
                destCell.setCellType(Cell.CELL_TYPE_BLANK);
                ((PoiCellData) cellData).writeToCell(destCell, context, this);
                copyMergedRegions(cellData, targetCellRef);
            } catch (Exception e) {
                logger.error("Failed to write a cell with {} and context keys {}", cellData, context.toMap().keySet(), e);
            }
        }
    }

    @Override
    public void resetArea(AreaRef areaRef) {
        // removing merged regions
        Sheet destSheet = workbook.getSheet(areaRef.getSheetName());
        int numMergedRegions = destSheet.getNumMergedRegions();
        for (int i = 0; i < numMergedRegions; i++) {
            destSheet.removeMergedRegion(i);
        }
    }

    private void copyMergedRegions(CellData sourceCellData, CellRef destCell) {
        if (sourceCellData.getSheetName() == null) {
            throw new IllegalArgumentException("Sheet name is null in copyMergedRegions");
        }
        PoiSheetData sheetData = (PoiSheetData) sheetMap.get(sourceCellData.getSheetName());
        CellRangeAddress cellMergedRegion = null;
        for (CellRangeAddress mergedRegion : sheetData.getMergedRegions()) {
            if (mergedRegion.getFirstRow() == sourceCellData.getRow() && mergedRegion.getFirstColumn() == sourceCellData.getCol()) {
                cellMergedRegion = mergedRegion;
                break;
            }
        }
        if (cellMergedRegion != null) {
            findAndRemoveExistingCellRegion(destCell);
            Sheet destSheet = workbook.getSheet(destCell.getSheetName());
            destSheet.addMergedRegion(new CellRangeAddress(destCell.getRow(), destCell.getRow() + cellMergedRegion.getLastRow() - cellMergedRegion.getFirstRow(),
                    destCell.getCol(), destCell.getCol() + cellMergedRegion.getLastColumn() - cellMergedRegion.getFirstColumn()));
        }
    }

    private void findAndRemoveExistingCellRegion(CellRef cellRef) {
        Sheet destSheet = workbook.getSheet(cellRef.getSheetName());
        int numMergedRegions = destSheet.getNumMergedRegions();
        for (int i = 0; i < numMergedRegions; i++) {
            CellRangeAddress mergedRegion = destSheet.getMergedRegion(i);
            if (mergedRegion.getFirstRow() <= cellRef.getRow() && mergedRegion.getLastRow() >= cellRef.getRow() &&
                    mergedRegion.getFirstColumn() <= cellRef.getCol() && mergedRegion.getLastColumn() >= cellRef.getCol()) {
                destSheet.removeMergedRegion(i);
                break;
            }
        }
    }

    public void setFormula(CellRef cellRef, String formulaString) {
        if (cellRef == null || cellRef.getSheetName() == null) return;
        Sheet sheet = workbook.getSheet(cellRef.getSheetName());
        if (sheet == null) {
            sheet = workbook.createSheet(cellRef.getSheetName());
        }
        Row row = sheet.getRow(cellRef.getRow());
        if (row == null) {
            row = sheet.createRow(cellRef.getRow());
        }
        Cell poiCell = row.getCell(cellRef.getCol());
        if (poiCell == null) {
            poiCell = row.createCell(cellRef.getCol());
        }
        try {
            poiCell.setCellFormula(formulaString);
        } catch (Exception e) {
            logger.error("Failed to set formula = " + formulaString + " into cell = " + cellRef.getCellName(), e);
        }
    }

    public void clearCell(CellRef cellRef) {
        if (cellRef == null || cellRef.getSheetName() == null) return;
        Sheet sheet = workbook.getSheet(cellRef.getSheetName());
        if (sheet == null) return;
        removeCellComment(sheet, cellRef.getRow(), cellRef.getCol());
        Row row = sheet.getRow(cellRef.getRow());
        if (row == null) return;
        Cell cell = row.getCell(cellRef.getCol());
        if (cell == null) {
            if (sheet.getCellComment(cellRef.getRow(), cellRef.getCol()) != null) {
                cell = row.createCell(cellRef.getCol());
                cell.removeCellComment();
            }
            return;
        }
        cell.setCellType(Cell.CELL_TYPE_BLANK);
        cell.setCellStyle(workbook.getCellStyleAt((short) 0));
        if (cell.getCellComment() != null) {
            cell.removeCellComment();
        }
        findAndRemoveExistingCellRegion(cellRef);
    }

    private void removeCellComment(Sheet sheet, int row, int col) {
        Comment comment = sheet.getCellComment(row, col);
    }

    public List<CellData> getCommentedCells() {
        List<CellData> commentedCells = new ArrayList<CellData>();
        for (SheetData sheetData : sheetMap.values()) {
            PoiSheetData poiSheetData = (PoiSheetData) sheetData;
            for (RowData rowData : sheetData) {
                if (rowData == null) continue;
                int row = ((PoiRowData) rowData).getRow().getRowNum();
                List<CellData> cellDataList = readCommentsFromSheet(((PoiSheetData) sheetData).getSheet(), row);
                commentedCells.addAll(cellDataList);
            }
        }
        return commentedCells;
    }

    private void addImage(AreaRef areaRef, int imageIdx) {
        CreationHelper helper = workbook.getCreationHelper();
        Sheet sheet = workbook.getSheet(areaRef.getSheetName());
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(areaRef.getFirstCellRef().getCol());
        anchor.setRow1(areaRef.getFirstCellRef().getRow());
        anchor.setCol2(areaRef.getLastCellRef().getCol());
        anchor.setRow2(areaRef.getLastCellRef().getRow());
        drawing.createPicture(anchor, imageIdx);
    }

    public void addImage(AreaRef areaRef, byte[] imageBytes, ImageType imageType) {
        int poiPictureType = findPoiPictureTypeByImageType(imageType);
        int pictureIdx = workbook.addPicture(imageBytes, poiPictureType);
        addImage(areaRef, pictureIdx);
    }

    public void write() throws IOException {
        if (outputStream == null) {
            throw new IllegalStateException("Cannot write a workbook with an uninitialized output stream");
        }
        if (workbook == null) {
            throw new IllegalStateException("Cannot write an uninitialized workbook");
        }
        workbook.write(outputStream);
        outputStream.close();
    }


    private int findPoiPictureTypeByImageType(ImageType imageType) {
        int poiType = -1;
        if (imageType == null) {
            throw new IllegalArgumentException("Image type is undefined");
        }
        switch (imageType) {
            case PNG:
                poiType = Workbook.PICTURE_TYPE_PNG;
                break;
            case JPEG:
                poiType = Workbook.PICTURE_TYPE_JPEG;
                break;
            case EMF:
                poiType = Workbook.PICTURE_TYPE_EMF;
                break;
            case WMF:
                poiType = Workbook.PICTURE_TYPE_WMF;
                break;
            case DIB:
                poiType = Workbook.PICTURE_TYPE_DIB;
                break;
            case PICT:
                poiType = Workbook.PICTURE_TYPE_PICT;
                break;
        }
        return poiType;
    }

    private List<CellData> readCommentsFromSheet(Sheet sheet, int rowNum) {
        List<CellData> commentDataCells = new ArrayList<>();
        for (int i = 0; i <= lastCommentedColumn; i++) {
            Comment comment = sheet.getCellComment(rowNum, i);
            if (comment != null && comment.getString() != null) {
                CellData cellData = new CellData(new CellRef(sheet.getSheetName(), rowNum, i));
                cellData.setCellComment(comment.getString().getString());
                commentDataCells.add(cellData);
            }
        }
        return commentDataCells;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public CellStyle getCellStyle(CellRef cellRef) {
        SheetData sheetData = sheetMap.get(cellRef.getSheetName());
        PoiCellData cellData = (PoiCellData) sheetData.getRowData(cellRef.getRow()).getCellData(cellRef.getCol());
        return cellData.getCellStyle();
    }

    @Override
    public boolean deleteSheet(String sheetName) {
        if (super.deleteSheet(sheetName)) {
            int sheetIndex = workbook.getSheetIndex(sheetName);
            workbook.removeSheetAt(sheetIndex);
            return true;
        } else {
            logger.warn("Failed to find '{}' worksheet in a sheet map. Skipping the deletion.", sheetName);
            return false;
        }
    }

    @Override
    public void setHidden(String sheetName, boolean hidden) {
        int sheetIndex = workbook.getSheetIndex(sheetName);
        workbook.setSheetHidden(sheetIndex, hidden);
    }

    @Override
    public void updateRowHeight(String srcSheetName, int srcRowNum, String targetSheetName, int targetRowNum) {
        if (isSXSSF) return;
        SheetData sheetData = sheetMap.get(srcSheetName);
        RowData rowData = sheetData.getRowData(srcRowNum);
        Sheet sheet = workbook.getSheet(targetSheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(targetSheetName);
        }
        Row targetRow = sheet.getRow(targetRowNum);
        if (targetRow == null) {
            targetRow = sheet.createRow(targetRowNum);
        }
        short srcHeight = rowData != null ? (short) rowData.getHeight() : sheet.getDefaultRowHeight();
        targetRow.setHeight(srcHeight);
    }
}
