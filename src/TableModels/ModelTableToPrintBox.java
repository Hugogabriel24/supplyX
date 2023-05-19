/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModels;

import TablesGetSetters.ProdutosToPrintBox;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTableToPrintBox extends AbstractTableModel {

    private List<ProdutosToPrintBox> linhas;
    private String[] colunas = new String[]{"ID", "Caixa"};

    public ModelTableToPrintBox() {
        linhas = new ArrayList<ProdutosToPrintBox>();
    }

    public ModelTableToPrintBox(List<ProdutosToPrintBox> lista) {
        linhas = new ArrayList<ProdutosToPrintBox>(lista);
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        /*
        switch(columnIndex){
            case 0:
                return Integer.class;
            default:
                return String.class;
        }
         */
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProdutosToPrintBox ppt = linhas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return ppt.getID();
            case 1:
                return ppt.getCaixa();

            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    //modifica na linha e coluna especificada
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ProdutosToPrintBox ppt = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

        switch (columnIndex) { // Seta o valor do campo respectivo
            case 0:
                ppt.setID(aValue.toString());
                break;
            case 1:
                ppt.setCaixa(aValue.toString());
                break;

            default:
            // Isto não deveria acontecer...             
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    //modifica na linha especificada
    public void setValueAt(ProdutosToPrintBox aValue, int rowIndex) {
        ProdutosToPrintBox ppt = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

        ppt.setID(aValue.getID());
        ppt.setCaixa(aValue.getCaixa());

        fireTableCellUpdated(rowIndex, 0);
        fireTableCellUpdated(rowIndex, 1);

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public ProdutosToPrintBox getProdutos(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void addProdutosPPT(ProdutosToPrintBox ft) {
        // Adiciona o registro.
        linhas.add(ft);
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    /* Remove a linha especificada. */
    public void remove(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    /* Adiciona uma lista de Cliente ao final dos registros. */
    public void addLista(List<ProdutosToPrintBox> ft) {
        // Pega o tamanho antigo da tabela.
        int tamanhoAntigo = getRowCount();

        // Adiciona os registros.
        linhas.addAll(ft);
        fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
    }

    /* Remove todos os registros. */
    public void limpar() {
        linhas.clear();
        fireTableDataChanged();
    }

    /* Verifica se este table model esta vazio. */
    public boolean isEmpty() {
        return linhas.isEmpty();
    }

}
