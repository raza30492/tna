package com.jazasoft.tna.dozerconverter;

import com.jazasoft.tna.entity.Label;
import org.dozer.DozerConverter;

import java.util.Date;

/**
 * Created by mtalam on 6/14/2017.
 */
public class LongLabelConverter extends DozerConverter<Long,Label> {
    public LongLabelConverter() {
        super(Long.class,Label.class);
    }
    // returning always null because it is one way conversion from entity to id only.
    @Override
    public Label convertTo(Long aLong,Label label){


        return null;
    }
    @Override
    public Long convertFrom(Label label,Long along){
        if(label == null)
            return  null;

        return label.getId();
    }
}
