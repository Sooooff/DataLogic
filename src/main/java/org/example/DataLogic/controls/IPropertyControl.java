package org.example.DataLogic.controls;

import org.openxava.view.View;
import javax.servlet.http.HttpServletRequest;

public interface IPropertyControl {
    void setProperty(String property);
    void setView(View view);
    void setRequest(HttpServletRequest request);
    String getAsHTML() throws Exception;
    String getURI();
}