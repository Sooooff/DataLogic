package org.example.DataLogic.controls;

import org.openxava.view.View;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MonedaControl implements IPropertyControl {

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
    public String getURI() {
        return null;
    }

    @Override
    public String getAsHTML() throws Exception {
        Object value = null;
        if (view != null && property != null) {
            value = view.getValue(property);
        }

        String formattedValue = "0.00";

        if (value instanceof BigDecimal) {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            formattedValue = df.format((BigDecimal) value);
        } else if (value != null) {
            formattedValue = value.toString();
        }

        String fieldName = this.property != null ? this.property : "monto";

        return "<div style='margin: 10px 0;'>" +
                "<div style='display: flex; align-items: center;'>" +
                "<span style='background: #f8f9fa; padding: 8px 12px; border: 1px solid #ddd; border-right: none; font-weight: bold;'>C$</span>" +
                "<input type='text' name='" + fieldName + "' " +
                "value='" + formattedValue + "' " +
                "class='xava_input' " +
                "style='padding: 8px; border: 1px solid #ddd; width: 200px;' />" +
                "</div>" +
                "</div>";}
}
