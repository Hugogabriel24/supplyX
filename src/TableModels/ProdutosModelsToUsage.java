/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModels;

import TablesGetSetters.ProdutosToUsage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hugogabriel
 */
public class ProdutosModelsToUsage extends AbstractTableModel {

    private List<ProdutosToUsage> linhas;
    private String[] colunas = new String[]{"ID", "Produto", "Descricao", "CodigoB", "Serial", "Marca","Qtde"};

    public ProdutosModelsToUsage() {
        linhas = new ArrayList<ProdutosToUsage>();
    }

    public ProdutosModelsToUsage(List<ProdutosToUsage> lista) {
        linhas = new ArrayList<ProdutosToUsage>(lista);
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
        ProdutosToUsage pp = linhas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return pp.getID();
            case 1:
                return pp.getProduto();
            case 2:
                return pp.getDescricao();
            case 3:
                return pp.getCodb();
            case 4:
                return pp.getSerial();
            case 5:
                return pp.getMarca();
            case 6:
                return pp.getQtde();

            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    //modifica na linha e coluna especificada
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ProdutosToUsage pp = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

        switch (columnIndex) { // Seta o valor do campo respectivo
            case 0:
                pp.setID(aValue.toString());
                break;
            case 1:
                pp.setProduto(aValue.toString());
                break;
            case 2:
                pp.setDescricao(aValue.toString());
                break;
            case 3:
                pp.setCodb(aValue.toString());
                break;
            case 4:
                pp.setSerial(aValue.toString());
                break;
            case 5:
                pp.setMarca(aValue.toString());
                break;
            case 6:
                pp.setQtde(aValue.toString());
                break;

            default:
            // Isto não deveria acontecer...             
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    //modifica na linha especificada
    public void setValueAt(ProdutosToUsage aValue, int rowIndex) {
        ProdutosToUsage pp = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

        pp.setID(aValue.getID());
        pp.setProduto(aValue.getProduto());
        pp.setDescricao(aValue.getDescricao());
        pp.setCodb(aValue.getCodb());
        pp.setSerial(aValue.getSerial());
        pp.setMarca(aValue.getMarca());
        pp.setQtde(aValue.getQtde());

        fireTableCellUpdated(rowIndex, 0);
        fireTableCellUpdated(rowIndex, 1);
        fireTableCellUpdated(rowIndex, 2);
        fireTableCellUpdated(rowIndex, 3);
        fireTableCellUpdated(rowIndex, 4);
        fireTableCellUpdated(rowIndex, 5);
        fireTableCellUpdated(rowIndex, 6);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public ProdutosToUsage getProdutos(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void addProdutosPP(ProdutosToUsage f) {
        // Adiciona o registro.
        linhas.add(f);
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    /* Remove a linha especificada. */
    public void remove(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    /* Adiciona uma lista de Cliente ao final dos registros. */
    public void addLista(List<ProdutosToUsage> f) {
        // Pega o tamanho antigo da tabela.
        int tamanhoAntigo = getRowCount();

        // Adiciona os registros.
        linhas.addAll(f);
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
