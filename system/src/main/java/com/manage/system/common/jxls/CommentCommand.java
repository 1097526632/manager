package com.manage.system.common.jxls;

import com.alibaba.fastjson.JSONObject;
import com.manage.system.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.AreaRef;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.Size;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;

public class CommentCommand extends AbstractCommand {
    private static final String COMMAND_NAME = "comment";
    private Area area;
    private String value;
    private String type;

    @Override
    public Command addArea(Area area) {
        if( areaList.size() >= 1){
            throw new IllegalArgumentException("You can add only a single area to 'Comment' command");
        }
        this.area = area;
        return super.addArea(area);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public Size applyAt(CellRef cellRef, Context context) {
        Transformer transformer = getTransformer();
        AreaRef areaRef = new AreaRef(cellRef, area.getSize());
        area.applyAt(cellRef, context);
        if(transformer instanceof PoiTransformer){
            comment(cellRef, context, (PoiTransformer)transformer);
        }
        return new Size(1, 1);
    }

    protected void comment(CellRef cellRef, Context context, PoiTransformer transformer){
        Object valueObj=null;
        valueObj = getTransformationConfig().getExpressionEvaluator().evaluate(value, context.toMap());

        Sheet sheet = transformer.getWorkbook().getSheet(cellRef.getSheetName());
        Row row = sheet.getRow(cellRef.getRow());
        if(row == null){
            row = sheet.createRow(cellRef.getRow());
        }
        Cell cell = row.getCell(cellRef.getCol());
        if(cell == null){
            cell = row.createCell(cellRef.getCol());
        }
        Comment comment=null;
        Drawing drawing=sheet.createDrawingPatriarch();
        ClientAnchor clientAnchor=null;
        if(row instanceof HSSFRow){
            //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
            //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
            clientAnchor=new HSSFClientAnchor(0,0,0,0, (short)4, 2 ,(short) 6, 5);
        }else if(row instanceof XSSFRow){
            clientAnchor=new XSSFClientAnchor(0,0,0,0, (short)4, 2 ,(short) 6, 5);
        }
        comment=drawing.createCellComment(clientAnchor);

        RichTextString richTextString=null;
        String commentStr=value;
        if("json".equalsIgnoreCase(type)){
            commentStr=valueObj!=null? JSONObject.toJSONString(valueObj):"";
        }

        if(row instanceof HSSFRow){
            richTextString=new HSSFRichTextString(commentStr);
        }else if(row instanceof XSSFRow){
            richTextString=new XSSFRichTextString(commentStr);
        }
        if(StringUtils.isNotBlank(commentStr)){
            comment.setString(richTextString);
            cell.setCellComment(comment);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
