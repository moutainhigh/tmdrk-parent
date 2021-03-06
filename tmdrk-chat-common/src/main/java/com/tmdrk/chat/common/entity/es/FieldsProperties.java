package com.tmdrk.chat.common.entity.es;

import com.tmdrk.chat.common.entity.es.fieldAnnotation.Analyzer;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.Fields;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.Type;

/**
 * @ClassName FieldsProperties
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/19 11:49
 * @Version 1.0
 **/
public class FieldsProperties {
    @Properties
    private String field1;
    @Properties(type=@Type(value="text"),analyzer = @Analyzer(value="ik_smart",use=true),fields = @Fields(value=Default.class,use=true))
    private String field2;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
