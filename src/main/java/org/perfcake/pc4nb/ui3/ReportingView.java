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
package org.perfcake.pc4nb.ui3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.core.model.ReporterModel;
import org.perfcake.pc4nb.core.model.ReportingModel;
import static org.perfcake.pc4nb.ui3.SizeConstraints.INSET;
import static org.perfcake.pc4nb.ui3.SizeConstraints.PERFCAKE_RECTANGLE_HEIGHT;
import static org.perfcake.pc4nb.ui3.SizeConstraints.SECOND_LEVEL_RECTANGLE_WIDTH;

/**
 *
 * @author Andrej Halaj
 */
public class ReportingView extends PC4NBView {
    private JMenuItem addComponent = new JMenuItem("Add new reporter");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new ReporterTansferHandler();

    public ReportingView(int x, int y, int width) {
        super(x, y, width);
        setColor(Color.RED);
        setHeader("Reporting");

        addComponent.addActionListener(new AddReporterListener());

        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        setTransferHandler(transferHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(getColor());
        
        ReportingModel model = (ReportingModel) getModel();

        if (model != null && model.getReporting() != null) {
            List<Reporter> reporters = model.getReporting().getReporter();

            int currentX = INSET;
            int currentY = TOP_INDENT;

            this.removeAll();

            for (Reporter reporter : reporters) {
                ReporterView newReporter = new ReporterView(currentX, currentY, reporter.getClazz());
                newReporter.setModel(ModelMap.getDefault().getPC4NBModelFor(reporter));
                this.add(newReporter);

                if (currentX + 2 * (SECOND_LEVEL_RECTANGLE_WIDTH + INSET) > getWidth()) {
                    currentX = INSET;
                    currentY += PERFCAKE_RECTANGLE_HEIGHT + INSET;
                } else {
                    currentX += SECOND_LEVEL_RECTANGLE_WIDTH + INSET;
                }
            }
        }
    }

    @Override
    public void recomputeHeight() {
        super.recomputeHeight();
        ReportingModel model = (ReportingModel) getModel();

        if (model != null && model.getReporting() != null) {
            List<Reporter> reporters = model.getReporting().getReporter();
            int columns = (int) ((getWidth() - INSET) / (SECOND_LEVEL_RECTANGLE_WIDTH + INSET));
            this.setHeight((int) (TOP_INDENT + (Math.ceil((double) reporters.size() / (double) columns) * (PERFCAKE_RECTANGLE_HEIGHT + INSET))));
        }
    }

    private final class ReporterTansferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(ReporterModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                ReporterModel model = (ReporterModel) support.getTransferable().getTransferData(ReporterModel.DATA_FLAVOR);
                ((ReportingModel) getModel()).addReporter(model.getReporter());

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }
    
    private class AddReporterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddReporterAction action = new AddReporterAction(getModel());
            action.execute();
        }
    }
}