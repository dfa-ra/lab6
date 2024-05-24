package com.ra.common.commands;

import com.ra.common.forms.Form;
import com.ra.common.forms.HelpForm;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class CommandType implements Serializable {
    private final int argumentCount_min;
    private final int argumentCount_max;
    private final boolean withForm;
    private HelpForm form = null;
    private HelpForm helpForm = null;
    private boolean permissionUpdate;

    public CommandType(int argumentCount, boolean withForm, boolean permissionUpdate) {
        this.argumentCount_min = argumentCount;
        this.argumentCount_max = argumentCount;
        this.withForm = withForm;
        this.permissionUpdate = permissionUpdate;
    }
    public CommandType(int argumentCount_min, int argumentCount_max, boolean withForm, boolean permissionUpdate) {
        this.argumentCount_min = argumentCount_min;
        this.argumentCount_max = argumentCount_max;
        this.withForm = withForm;
        this.permissionUpdate = permissionUpdate;
    }
    public CommandType(int argumentCount_min, int argumentCount_max, boolean withForm, HelpForm form, HelpForm helpForms, boolean permissionUpdate) {
        this.argumentCount_min = argumentCount_min;
        this.argumentCount_max = argumentCount_max;
        this.withForm = withForm;
        this.form = form;
        this.helpForm = helpForms;
        this.permissionUpdate = permissionUpdate;
    }

    public CommandType(int argumentCount, boolean withForm, HelpForm form, HelpForm helpForms, boolean permissionUpdate) {
        this.argumentCount_min = argumentCount;
        this.argumentCount_max = argumentCount;
        this.withForm = withForm;
        this.form = form;
        this.helpForm = helpForms;
        this.permissionUpdate = permissionUpdate;
    }
}
