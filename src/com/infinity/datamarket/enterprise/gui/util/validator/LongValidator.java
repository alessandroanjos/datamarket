package com.infinity.datamarket.enterprise.gui.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class LongValidator implements Validator{

	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		if(arg2 == null) return;
		try{
			new Long(arg2.toString());
		}catch(NumberFormatException e){			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação:", "O campo deve ser numérico");
			throw new ValidatorException(message);
		}
	}

}
