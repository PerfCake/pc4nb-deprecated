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
package org.perfcake.pc4nb.wizards.visuals;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import org.openide.util.Exceptions;
import org.perfcake.message.sender.MessageSender;
import org.perfcake.model.Scenario;
import org.perfcake.pc4nb.core.model.SenderModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.reflect.ComponentScanner;

public final class SenderVisualPanel extends VisualPanelWithProperties {
    public static final String SENDER_PACKAGE = "org.perfcake.message.sender";

    /**
     * Creates new form ScenarioVisualPanel2
     */
    public SenderVisualPanel() {
        setModel(new SenderModel(new Scenario.Sender()));
        ComponentScanner scanner = new ComponentScanner();
        Set<Class<? extends MessageSender>> subTypes = scanner.findComponentsOfType(MessageSender.class, SENDER_PACKAGE);

        Set<String> components = new HashSet<>();
        
        for (Class<? extends MessageSender> sender : subTypes) {
            components.add(sender.getSimpleName());
        }
        
        ComponentPropertiesScanner propertyScanner = new ComponentPropertiesScanner();

        for (String component : components) {
            try {
                putToComponentPropertiesMap(component, propertyScanner.getPropertiesOfComponent(Class.forName(SENDER_PACKAGE + "." + component)));
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        initComponents();
        
        try {
            listProperties((String) senderSelection.getSelectedItem());
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            System.err.println("Class not found " + ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "Sender";
    }

    public JComboBox getSenderSelection() {
        return senderSelection;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        senderSelection = new javax.swing.JComboBox();
        senderTypeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        propertiesLabel = new javax.swing.JLabel();

        Set<String> componentNames = getComponentPropertiesModelMap().keySet();
        String[] componentNamesArray = new String[componentNames.size()];
        componentNames.toArray(componentNamesArray);
        senderSelection.setModel(new DefaultComboBoxModel(componentNamesArray));

        senderSelection.addItemListener (new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    listProperties((String) senderSelection.getSelectedItem());
                } catch (ClassNotFoundException | NoSuchFieldException ex) {
                    // blah
                }
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(senderTypeLabel, org.openide.util.NbBundle.getMessage(SenderVisualPanel.class, "SenderVisualPanel.senderTypeLabel.text")); // NOI18N

        propertiesTable.setModel(getPropertiesTableModel());
        jScrollPane1.setViewportView(propertiesTable);

        org.openide.awt.Mnemonics.setLocalizedText(propertiesLabel, org.openide.util.NbBundle.getMessage(SenderVisualPanel.class, "SenderVisualPanel.propertiesLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(propertiesLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(senderTypeLabel)
                    .addComponent(senderSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(senderTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(senderSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(propertiesLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JComboBox senderSelection;
    private javax.swing.JLabel senderTypeLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public JTable getPropertiesTable() {
        return propertiesTable;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}