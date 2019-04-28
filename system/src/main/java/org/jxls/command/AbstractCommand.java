package org.jxls.command;

import org.jxls.area.Area;
import org.jxls.transform.TransformationConfig;
import org.jxls.transform.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements basic command methods and is a convenient base class for other commands
 * @author Leonid Vysochyn
 *         Date: 21.03.2009
 */
public abstract class AbstractCommand implements Command {
    private Logger logger = LoggerFactory.getLogger(AbstractCommand.class);
    public List<Area> areaList = new ArrayList<Area>();
    private String shiftMode;

    public Command addArea(Area area) {
        areaList.add(area);
        area.setParentCommand(this);
        return this;
    }

    public void reset() {
        for (Area area : areaList) {
            area.reset();
        }
    }

    @Override
    public void setShiftMode(String mode) {
        if( mode != null ){
            if( mode.equalsIgnoreCase(Command.INNER_SHIFT_MODE) || mode.equalsIgnoreCase(Command.ADJACENT_SHIFT_MODE)){
                shiftMode = mode;
            }else{
                logger.error("Cannot set cell shift mode to " + mode + " for command: " + getName());
            }
        }
    }

    @Override
    public String getShiftMode() {
        return shiftMode;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    protected Transformer getTransformer(){
        if( areaList.isEmpty() ) return null;
        return areaList.get(0).getTransformer();
    }

    protected TransformationConfig getTransformationConfig(){
        return getTransformer().getTransformationConfig();
    }
}
