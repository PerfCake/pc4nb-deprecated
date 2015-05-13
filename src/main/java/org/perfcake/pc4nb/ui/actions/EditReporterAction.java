/*
 * Copyright 2015 Andrej Halaj.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.perfcake.pc4nb.ui.actions;

import java.util.List;
import java.util.Properties;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.ReporterModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.wizards.ReporterWizardPanel;
import static org.perfcake.pc4nb.ui.wizards.visuals.ReporterVisualPanel.REPORTER_PACKAGE;

/**
 *
 * @author Andrej Halaj
 */
public class EditReporterAction extends AbstractPC4NBAction {
    private ReporterWizardPanel wizardPanel;
    private ReporterModel reporterModel;

    public EditReporterAction(ReporterModel reporterModel) {
        this.reporterModel = reporterModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new ReporterWizardPanel();
        wizardPanel.getComponent().setModel(reporterModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        reporterModel.setClazz((String) wiz.getProperty("reporter-clazz"));
        reporterModel.setEnabled((boolean) wiz.getProperty("reporter-enabled"));

        List<Property> properties = (List<Property>) wiz.getProperty("reporter-properties");

        Properties defaultValues = new Properties();

        try {
            defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(Class.forName(REPORTER_PACKAGE + "." + wiz.getProperty("reporter-clazz")));
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        List<Property> reporterProperties = reporterModel.getProperty();
            
        for (int i = reporterProperties.size() - 1; i >= 0; i--) {
            reporterModel.removeProperty(reporterProperties.get(i));
        }
        
        for (Property property : properties) {
            String propertyName = property.getName();
            String propertyValue = property.getValue();

            if (!propertyValue.equals(defaultValues.get(propertyName))) {
                Property newProperty = new Property();
                newProperty.setName(propertyName);
                newProperty.setValue(propertyValue);

                reporterModel.addProperty(newProperty);
            }
        }
    }
}
