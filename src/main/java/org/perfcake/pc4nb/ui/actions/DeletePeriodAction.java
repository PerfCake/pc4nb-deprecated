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
import org.openide.WizardDescriptor;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.pc4nb.model.DestinationModel;
import org.perfcake.pc4nb.model.PC4NBModel;

/**
 *
 * @author Andrej Halaj
 */
public class DeletePeriodAction extends AbstractPC4NBAction {
    private PC4NBModel from;
    private List<Period> toRemove;

    public DeletePeriodAction(PC4NBModel from, List<Period> toRemove) {
        this.from = from;
        this.toRemove = toRemove;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        return null;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        DestinationModel destinationModel = (DestinationModel) from;
        
        for (Period period : toRemove) {
            destinationModel.removePeriod(period);
        }
    }
}
