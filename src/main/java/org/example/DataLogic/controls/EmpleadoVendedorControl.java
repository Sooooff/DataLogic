package org.example.DataLogic.controls;


import org.openxava.view.View;

import javax.servlet.http.HttpServletRequest;

public class EmpleadoVendedorControl implements IPropertyControl {

    private String property;
    private View view;
    private HttpServletRequest request;

    @Override
    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getAsHTML() throws Exception {
        Object value = view.getValue(property);
        String currentValue = value != null ? value.toString() : "";

        return "<select name='" + property + "' class='xava_input'>" +
                "<option value=''>-- Seleccione Vendedor --</option>" +
                "<option value='1' " + ("1".equals(currentValue) ? "selected" : "") + ">Vendedor 1</option>" +
                "<option value='2' " + ("2".equals(currentValue) ? "selected" : "") + ">Vendedor 2</option>" +
                "</select>" +
                "<small style='color: #666; margin-left: 10px;'>Control personalizado</small>";
    }

    @Override
    public String getURI() {

        return null;
    }
}