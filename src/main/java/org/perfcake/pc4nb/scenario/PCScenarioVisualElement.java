/*
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
package org.perfcake.pc4nb.scenario;

import org.perfcake.pc4nb.model.SenderModel;
import org.perfcake.pc4nb.model.GeneratorModel;
import org.perfcake.pc4nb.model.ReportingModel;
import org.perfcake.pc4nb.model.ValidationModel;
import org.perfcake.pc4nb.model.ScenarioModel;
import org.perfcake.pc4nb.model.MessagesModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBView;
import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import net.miginfocom.swing.MigLayout;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.awt.UndoRedo;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;
import org.perfcake.model.Scenario;
import org.perfcake.pc4nb.model.PropertiesModel;
import org.perfcake.pc4nb.model.RunModel;
import org.perfcake.pc4nb.ui.palette.PC4NBPaletteActions;
import org.perfcake.pc4nb.ui.palette.PerfCakeComponentCategoryNodeContainer;
import org.perfcake.pc4nb.ui.*;

@MultiViewElement.Registration(
        displayName = "#LBL_PCScenario_VISUAL",
        iconBase = "org/perfcake/pc4nb/favicon.png",
        mimeType = "text/pcscenario+xml",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "PCScenarioVisual",
        position = 1000
)
@Messages("LBL_PCScenario_VISUAL=Designer")
public final class PCScenarioVisualElement extends AbstractPC4NBView implements MultiViewElement {

    private PCScenarioDataObject obj;
    private JToolBar toolbar = new JToolBar();
    private MultiViewElementCallback callback;
    private PaletteController paletteController = null;
    JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    RunView runView = new RunView();
    GeneratorView generatorView = new GeneratorView();
    SenderView senderView = new SenderView();
    ReportingView reportingView = new ReportingView();
    ValidationView validationView = new ValidationView();
    MessagesView messagesView = new MessagesView();
    PropertiesView propertiesView = new PropertiesView();

    public PCScenarioVisualElement(Lookup lkp) throws IOException {
        obj = lkp.lookup(PCScenarioDataObject.class);
        assert obj != null;

        initComponents();
        initPalette();
        refreshUI();

        obj.getPrimaryFile().addFileChangeListener(new FileChangeAdapter() {
            @Override
            public void fileChanged(FileEvent fe) {
                refreshUI();
            }
        });
    }

    @Override
    public String getName() {
        return "PCScenarioVisualElement";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(32500, 3250));
        setName(""); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
        private void initPalette() {
        Node palette = new AbstractNode(new PerfCakeComponentCategoryNodeContainer());
        PaletteActions actions = new PC4NBPaletteActions();

        paletteController = PaletteFactory.createPalette(palette, actions);
    }

    @Override
    public JComponent getVisualRepresentation() {
        return scrollPane;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return new ProxyLookup(obj.getLookup(), Lookups.fixed(paletteController));
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
    }

    @Override
    public void componentDeactivated() {
        ScenarioManager manager = new ScenarioManager();

        URI scenarioPath = obj.getPrimaryFile().toURI();

        ScenarioModel scenarioModel = (ScenarioModel) getModel();
        scenarioModel.setRun(((RunModel) runView.getModel()).getRun());
        scenarioModel.setGenerator(((GeneratorModel) generatorView.getModel()).getGenerator());
        scenarioModel.setSender(((SenderModel) senderView.getModel()).getSender());
        scenarioModel.setMessages(((MessagesModel) messagesView.getModel()).getMessages());
        scenarioModel.setReporting(((ReportingModel) reportingView.getModel()).getReporting());
        scenarioModel.setValidation(((ValidationModel) validationView.getModel()).getValidation());
        scenarioModel.setProperties(((PropertiesModel) propertiesView.getModel()).getProperties());

        try {
            OutputStream os = new FileOutputStream(Utilities.toFile(scenarioPath));
            manager.createXML(scenarioModel.getScenario(), os);
        } catch (ScenarioException | ScenarioManagerException ex) {
            ex.printStackTrace(System.out);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    private void refreshUI() {
        MigLayout layout = new MigLayout("fillx", "[center]20[center]", "[center]20[center]20[center]");
        this.setLayout(layout);

        URL scenarioUrl = obj.getPrimaryFile().toURL();

        ScenarioManager manager = new ScenarioManager();
        try {
            ScenarioModel scenarioModel = manager.createModel(scenarioUrl);

            Scenario scenario = scenarioModel.getScenario();
            this.setModel(scenarioModel);
            
            RunModel runModel = new RunModel(scenario.getRun());
            runView.setModel(runModel);

            GeneratorModel generatorModel = new GeneratorModel(scenario.getGenerator());
            generatorView.setModel(generatorModel);

            SenderModel senderModel = new SenderModel(scenario.getSender());
            senderView.setModel(senderModel);

            ReportingModel reportingModel = new ReportingModel(scenario.getReporting());
            reportingView.setModel(reportingModel);

            ValidationModel validationModel = new ValidationModel(scenario.getValidation());
            validationView.setModel(validationModel);

            MessagesModel messagesModel = new MessagesModel(scenario.getMessages());
            messagesView.setModel(messagesModel);

            PropertiesModel propertiesModel = new PropertiesModel(scenario.getProperties());
            propertiesView.setModel(propertiesModel);

            add(runView, "span 2, wrap, growx 150");
            add(generatorView, "span 2, wrap, growx 150");
            add(senderView, " span 2, wrap, growx 150");
            add(messagesView, "growx 150, growy 150");
            add(reportingView, "span 1 2, wrap, growx 150, growy 200");
            add(validationView, "wrap, growx 150, growy 150");
            add(propertiesView, "span 2, growx 150");

            revalidate();
            repaint();
        } catch (ScenarioException ex) {
            Exceptions.printStackTrace(ex);
        }

        scrollPane.setViewportView(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        return;
    }
}
