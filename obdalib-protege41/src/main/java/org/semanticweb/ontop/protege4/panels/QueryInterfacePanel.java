package org.semanticweb.ontop.protege4.panels;

/*
 * #%L
 * ontop-protege4
 * %%
 * Copyright (C) 2009 - 2013 KRDB Research Centre. Free University of Bozen Bolzano.
 * %%
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
 * #L%
 */

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

import org.semanticweb.ontop.owlrefplatform.core.queryevaluation.SPARQLQueryUtility;
import org.semanticweb.ontop.owlrefplatform.owlapi3.QuestOWLResultSet;
import org.semanticweb.ontop.protege4.core.MutableOBDAModel;
import org.semanticweb.ontop.protege4.gui.IconLoader;
import org.semanticweb.ontop.protege4.gui.action.OBDADataQueryAction;
import org.semanticweb.ontop.protege4.utils.DialogUtils;
import org.semanticweb.ontop.querymanager.QueryController;
import org.semanticweb.ontop.utils.OBDAPreferenceChangeListener;

/**
 * Creates a new panel to execute queries. Remember to execute the
 * setResultsPanel function to indicate where to display the results.
 */
public class QueryInterfacePanel extends JPanel implements SavedQueriesPanelListener, 
		TableModelListener, OBDAPreferenceChangeListener {

	/**
	 * Variable currentGroup is the group's id to which belongs the selected
	 * query Variable currentId is the query's id that is selected
	 */
	private static final long serialVersionUID = -5902798157183352944L;
	
	private DefaultStyledDocument styledDocument;
	
	private OBDADataQueryAction<QuestOWLResultSet> executeSelectAction;
	private OBDADataQueryAction<?> executeGraphQueryAction;
	private OBDADataQueryAction<?> executeEQLAction;
	private OBDADataQueryAction<String> retrieveUCQExpansionAction;
	private OBDADataQueryAction<String> retrieveUCQUnfoldingAction;
	private OBDADataQueryAction<?> retrieveEQLUnfoldingAction;
	
	private MutableOBDAModel apic;

	private QueryController qc;
	
	private double execTime = 0;
	private int fetchSizeCache = 100;
	
	private String currentGroup = "";  // default value
	private String currentId = "";  // default value
	
	/** 
	 * Creates new form QueryInterfacePanel
	 */
	public QueryInterfacePanel(MutableOBDAModel apic, QueryController qc) {
		this.qc = qc;
		this.apic = apic;
		
		initComponents();

		StyleContext style = new StyleContext();
		styledDocument = new DefaultStyledDocument(style);

		queryTextPane.setDocument(styledDocument);
		queryTextPane.setBackground(Color.WHITE);
		queryTextPane.setCaretColor(Color.BLACK);
		queryTextPane.addKeyListener(new CTRLEnterKeyListener());
	}

	private class CTRLEnterKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if ((e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_ENTER)) {
				cmdExecuteQueryActionPerformed(null);
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	public void setOBDAModel(MutableOBDAModel api) {
		this.apic = api;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        sparqlPopupMenu = new javax.swing.JPopupMenu();
        getSPARQLExpansion = new javax.swing.JMenuItem();
        getSPARQLSQLExpansion = new javax.swing.JMenuItem();
        pnlQueryButtons = new javax.swing.JPanel();
        pnlExecutionInfo = new javax.swing.JPanel();
        lblExecutionInfo = new javax.swing.JLabel();
        lblShow = new javax.swing.JLabel();
        txtFetchSize = new javax.swing.JTextField();
        chkShowAll = new javax.swing.JCheckBox();
        chkShowShortURI = new javax.swing.JCheckBox();
        cmdAttachPrefix = new javax.swing.JButton();
        cmdExecuteQuery = new javax.swing.JButton();
        cmdSaveChanges = new javax.swing.JButton();
        pnlQueryEditor = new javax.swing.JPanel();
        jLabelHeader = new javax.swing.JLabel();
        jScrollQueryPane = new javax.swing.JScrollPane();
        queryTextPane = new javax.swing.JTextPane();

        sparqlPopupMenu.setComponentPopupMenu(sparqlPopupMenu);

     // TODO Enable this after fixing the code in QuestStatement
//        getSPARQLExpansion.setText("Get expansion this UCQ...");
//        getSPARQLExpansion.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                getSPARQLExpansionActionPerformed(evt);
//            }
//        });
//        sparqlPopupMenu.add(getSPARQLExpansion);

        getSPARQLSQLExpansion.setText("Get expanded/unfolded query for this UCQ...");
        getSPARQLSQLExpansion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getSPARQLSQLExpansionActionPerformed(evt);
            }
        });
        sparqlPopupMenu.add(getSPARQLSQLExpansion);

        setLayout(new java.awt.GridBagLayout());

        pnlQueryButtons.setPreferredSize(new java.awt.Dimension(445, 30));
        pnlQueryButtons.setLayout(new java.awt.GridBagLayout());

        pnlExecutionInfo.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        pnlExecutionInfo.add(lblExecutionInfo, gridBagConstraints);

        lblShow.setText("Show: ");
        pnlExecutionInfo.add(lblShow, new java.awt.GridBagConstraints());

        txtFetchSize.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFetchSize.setText("100");
        txtFetchSize.setPreferredSize(new java.awt.Dimension(40, 20));
        pnlExecutionInfo.add(txtFetchSize, new java.awt.GridBagConstraints());

        chkShowAll.setText("All");
        chkShowAll.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chkShowAll.setPreferredSize(new java.awt.Dimension(55, 23));
        chkShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowAllActionPerformed(evt);
            }
        });
        pnlExecutionInfo.add(chkShowAll, new java.awt.GridBagConstraints());
        chkShowAll.doClick();

        chkShowShortURI.setText("Short IRI");
        chkShowShortURI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkShowShortURI.setMaximumSize(new java.awt.Dimension(95, 23));
        chkShowShortURI.setMinimumSize(new java.awt.Dimension(95, 23));
        chkShowShortURI.setPreferredSize(new java.awt.Dimension(95, 23));
        pnlExecutionInfo.add(chkShowShortURI, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        pnlQueryButtons.add(pnlExecutionInfo, gridBagConstraints);

        cmdAttachPrefix.setIcon(IconLoader.getImageIcon("images/attach.png"));
        cmdAttachPrefix.setText("Attach Prefixes");
        cmdAttachPrefix.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cmdAttachPrefix.setContentAreaFilled(false);
        cmdAttachPrefix.setIconTextGap(5);
        cmdAttachPrefix.setMaximumSize(new java.awt.Dimension(112, 26));
        cmdAttachPrefix.setMinimumSize(new java.awt.Dimension(112, 26));
        cmdAttachPrefix.setPreferredSize(new java.awt.Dimension(112, 26));
        cmdAttachPrefix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAttachPrefixActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        pnlQueryButtons.add(cmdAttachPrefix, gridBagConstraints);

        cmdExecuteQuery.setIcon(IconLoader.getImageIcon("images/execute.png"));
        cmdExecuteQuery.setMnemonic('x');
        cmdExecuteQuery.setText("Execute");
        cmdExecuteQuery.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cmdExecuteQuery.setContentAreaFilled(false);
        cmdExecuteQuery.setIconTextGap(5);
        cmdExecuteQuery.setMaximumSize(new java.awt.Dimension(82, 26));
        cmdExecuteQuery.setMinimumSize(new java.awt.Dimension(82, 26));
        cmdExecuteQuery.setPreferredSize(new java.awt.Dimension(82, 26));
        cmdExecuteQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExecuteQueryActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        pnlQueryButtons.add(cmdExecuteQuery, gridBagConstraints);

        cmdSaveChanges.setIcon(IconLoader.getImageIcon("images/save.png"));
        cmdSaveChanges.setText("Save Changes");
        cmdSaveChanges.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cmdSaveChanges.setContentAreaFilled(false);
        cmdSaveChanges.setIconTextGap(5);
        cmdSaveChanges.setMaximumSize(new java.awt.Dimension(112, 26));
        cmdSaveChanges.setMinimumSize(new java.awt.Dimension(112, 26));
        cmdSaveChanges.setPreferredSize(new java.awt.Dimension(112, 26));
        cmdSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveChangesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        pnlQueryButtons.add(cmdSaveChanges, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(pnlQueryButtons, gridBagConstraints);

        pnlQueryEditor.setLayout(new java.awt.BorderLayout());

        jLabelHeader.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelHeader.setForeground(new java.awt.Color(153, 153, 153));
        jLabelHeader.setText("  Query Editor");
        jLabelHeader.setMaximumSize(new java.awt.Dimension(68, 18));
        jLabelHeader.setMinimumSize(new java.awt.Dimension(68, 18));
        jLabelHeader.setPreferredSize(new java.awt.Dimension(68, 18));
        pnlQueryEditor.add(jLabelHeader, java.awt.BorderLayout.NORTH);

        queryTextPane.setFont(new java.awt.Font("Lucida Grande", 0, 14));
        queryTextPane.setComponentPopupMenu(sparqlPopupMenu);
        jScrollQueryPane.setViewportView(queryTextPane);

        pnlQueryEditor.add(jScrollQueryPane, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(pnlQueryEditor, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void chkShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowAllActionPerformed
    	if (chkShowAll.isSelected()) {
    		fetchSizeCache = getFetchSize();
    		txtFetchSize.setText(0+"");
    		txtFetchSize.setEditable(false);
    	} else {
    		txtFetchSize.setText(fetchSizeCache+"");
    		txtFetchSize.setEditable(true);
    	}
    }//GEN-LAST:event_chkShowAllActionPerformed

	private void getSPARQLExpansionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_getSPARQLExpansionActionPerformed
		Thread queryRunnerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				OBDADataQueryAction<?> action = QueryInterfacePanel.this.getRetrieveUCQExpansionAction();
				action.run(queryTextPane.getText());
			}
		});
		queryRunnerThread.start();
	}// GEN-LAST:event_getSPARQLExpansionActionPerformed

	private void getSPARQLSQLExpansionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_getSPARQLSQLExpansionActionPerformed
		Thread queryRunnerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				OBDADataQueryAction<?> action = QueryInterfacePanel.this.getRetrieveUCQUnfoldingAction();
				action.run(queryTextPane.getText());
			}
		});
		queryRunnerThread.start();
	}// GEN-LAST:event_getSPARQLSQLExpansionActionPerformed

	private void cmdAttachPrefixActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonAdvancedPropertiesActionPerformed
		SelectPrefixPanel dialog = new SelectPrefixPanel(apic.getPrefixManager(), queryTextPane);
		dialog.show();
	}// GEN-LAST:event_buttonAdvancedPropertiesActionPerformed

	private void cmdExecuteQueryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonExecuteActionPerformed
		try {
			// TODO Handle this such that there is a listener checking the progress of the execution
			Thread queryRunnerThread = new Thread(new Runnable() {
				@Override
				public void run() {
					SPARQLQueryUtility query = new SPARQLQueryUtility(queryTextPane.getText());
					OBDADataQueryAction<?> action = null;
					if (query.isSelectQuery() || query.isAskQuery()) {
						action = QueryInterfacePanel.this.getExecuteSelectAction();
					} else if ( (query.isConstructQuery() || query.isDescribeQuery()) ){
						action = QueryInterfacePanel.this.getExecuteGraphQueryAction();
					} else {
						JOptionPane.showMessageDialog(null, "This type of SPARQL expression is not handled. Please use SELECT, ASK, DESCRIBE, or CONSTRUCT.");
					}
					action.run(query.getQueryString());
					execTime = action.getExecutionTime();
					do {
						int rows = action.getNumberOfRows();
						updateStatus(rows);	
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							break;
						}
					} while (action.isRunning());
					updateStatus(action.getNumberOfRows());
				};
			});
			queryRunnerThread.start();
		} catch (Exception e) {
			DialogUtils.showQuickErrorDialog(null, e);
		}
	}// GEN-LAST:event_buttonExecuteActionPerformed


	private void cmdSaveChangesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonSaveActionPerformed
		final String query = queryTextPane.getText();
		if (!currentId.isEmpty()) {
			if (!currentGroup.isEmpty()) {
				qc.addQuery(query, currentId, currentGroup);
			} else {
				qc.addQuery(query, currentId);
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Please select first the query node that you would like to update",
					"Warning",
					JOptionPane.WARNING_MESSAGE);
		}		
	}// GEN-LAST:event_buttonSaveActionPerformed

	public void selectedQuerychanged(String new_group, String new_query, String new_id) {
		queryTextPane.setText(new_query);
		currentGroup = new_group;
		currentId = new_id;
	}

	public void setExecuteSelectAction(OBDADataQueryAction<QuestOWLResultSet> executeUCQAction) {
		this.executeSelectAction = executeUCQAction;
	}

	public OBDADataQueryAction<?> getExecuteSelectAction() {
		return executeSelectAction;
	}

	public void setExecuteGraphQueryAction(OBDADataQueryAction<?> action) {
		this.executeGraphQueryAction = action;
	}

	public OBDADataQueryAction<?> getExecuteGraphQueryAction() {
		return this.executeGraphQueryAction;
	}

	
	public void setExecuteEQLAction(OBDADataQueryAction<?> executeEQLAction) {
		this.executeEQLAction = executeEQLAction;
	}

	public OBDADataQueryAction<?> getExecuteEQLAction() {
		return executeEQLAction;
	}

	public void setRetrieveUCQExpansionAction(OBDADataQueryAction<String> retrieveUCQExpansionAction) {
		this.retrieveUCQExpansionAction = retrieveUCQExpansionAction;
	}

	public OBDADataQueryAction<String> getRetrieveUCQExpansionAction() {
		return retrieveUCQExpansionAction;
	}

	public void setRetrieveUCQUnfoldingAction(OBDADataQueryAction<String> retrieveUCQUnfoldingAction) {
		this.retrieveUCQUnfoldingAction = retrieveUCQUnfoldingAction;
	}

	public OBDADataQueryAction<?> getRetrieveUCQUnfoldingAction() {
		return retrieveUCQUnfoldingAction;
	}

	public void setRetrieveEQLUnfoldingAction(OBDADataQueryAction<?> retrieveEQLUnfoldingAction) {
		this.retrieveEQLUnfoldingAction = retrieveEQLUnfoldingAction;
	}

	public OBDADataQueryAction<?> getRetrieveEQLUnfoldingAction() {
		return retrieveEQLUnfoldingAction;
	}

	public void updateStatus(int rows) {
		Double time = Double.valueOf(execTime / 1000);
		String s = String.format("Execution time: %s sec - Number of rows retrieved: %,d ", time, rows);
		lblExecutionInfo.setText(s);
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkShowAll;
    private javax.swing.JCheckBox chkShowShortURI;
    private javax.swing.JButton cmdAttachPrefix;
    private javax.swing.JButton cmdExecuteQuery;
    private javax.swing.JButton cmdSaveChanges;
    private javax.swing.JMenuItem getSPARQLExpansion;
    private javax.swing.JMenuItem getSPARQLSQLExpansion;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JScrollPane jScrollQueryPane;
    private javax.swing.JLabel lblExecutionInfo;
    private javax.swing.JLabel lblShow;
    private javax.swing.JPanel pnlExecutionInfo;
    private javax.swing.JPanel pnlQueryButtons;
    private javax.swing.JPanel pnlQueryEditor;
    private javax.swing.JTextPane queryTextPane;
    private javax.swing.JPopupMenu sparqlPopupMenu;
    private javax.swing.JTextField txtFetchSize;
    // End of variables declaration//GEN-END:variables

	public void tableChanged(TableModelEvent e) {
		int rows = ((TableModel) e.getSource()).getRowCount();
		updateStatus(rows);
	}

	public boolean isShortURISelect() {
		return chkShowShortURI.isSelected();
	}
	
	public boolean isFetchAllSelect() {
		return chkShowAll.isSelected();
	}

	// TODO Remove this method after moving the GUI package to protege41 module.
	// The constant 100 is the same as the NEXT_FETCH_SIZE in OWLResultSetTableModel
	public boolean canGetMoreTuples() {
		return getFetchSize() > 100;
	}
	
	public String getQuery() {
		return queryTextPane.getText();
	}
	
	public int getFetchSize() {
		int fetchSize = 0;
		try {
			fetchSize = Integer.parseInt(txtFetchSize.getText());
		} catch (NumberFormatException e) {
			DialogUtils.showQuickErrorDialog(this, 
					new Exception("Invalid input: " + txtFetchSize.getText()), e.toString());
		}
		return fetchSize;
	}

	@Override
	public void preferenceChanged() {
		String query = queryTextPane.getText();
		queryTextPane.setText(query);
	}
}
