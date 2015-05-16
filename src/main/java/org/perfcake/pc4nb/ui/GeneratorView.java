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
package org.perfcake.pc4nb.ui;

import java.awt.Color;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javafx.scene.input.KeyCode;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import org.perfcake.pc4nb.model.GeneratorModel;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.ui.actions.EditGeneratorAction;

/**
 *
 * @author Andrej Halaj
 */
public class GeneratorView extends PC4NBView {
    private JMenuItem editComponent = new JMenuItem("Edit generator");
    private JPopupMenu menu = new JPopupMenu();
    TransferHandler transferHandler = new GeneratorTransferHandler();

    public GeneratorView(int x, int y, int width) {
        super(x, y, width);
        setDefaultBorder(new LineBorder(Color.RED, 1, true));
        setBorder(getDefaultBorder());
        setHeader("Generator");

        editComponent.addActionListener(new EditGeneratorListener());

        menu.add(editComponent);
        this.setComponentPopupMenu(menu);
        
        setTransferHandler(transferHandler);
        
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (hasFocus() && e.getKeyCode() == KeyCode.DELETE.ordinal()) {
                }
            }
        });
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        GeneratorModel generatorModel = (GeneratorModel) model;
        setHeader(generatorModel.getGenerator().getClazz());
    }

    private class EditGeneratorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditGeneratorAction action = new EditGeneratorAction((GeneratorModel) getModel());
            action.execute();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GeneratorModel.PROPERTY_CLASS))  {
            GeneratorModel generatorModel = (GeneratorModel) evt.getSource();
            setHeader(generatorModel.getGenerator().getClazz());
        }
    }
    
    private final class GeneratorTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(GeneratorModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                GeneratorModel model = (GeneratorModel) support.getTransferable().getTransferData(GeneratorModel.DATA_FLAVOR);
                setModel(model);
                revalidate();
                repaint();

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }
}