/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModels;

import TablesGetSetters.ProdutosPPOrc;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hugogabriel
 */
public class ProdutosModelsOrcamento extends AbstractTableModel {

    private List<ProdutosPPOrc> linhas;
    private String[] colunas = new String[]{"ID", "Produto", "Descricao", "CodigoB", "Serial", "Marca", "Preco", "Qtde", "Subtotal"};

    public ProdutosModelsOrcamento() {
        linhas = new ArrayList<ProdutosPPOrc>();
    }

    public ProdutosModelsOrcamento(List<ProdutosPPOrc> lista) {
        linhas = new ArrayList<ProdutosPPOrc>(lista);
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
        ProdutosPPOrc pp = linhas.get(rowIndex);

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
                return pp.getPreco();
            case 7:
                return pp.getQtde();
            case 8:
                return pp.getSubtotal();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    //modifica na linha e coluna especificada
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ProdutosPPOrc pp = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

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
                pp.setPreco(aValue.toString());
                break;
            case 7:
                pp.setQtde(aValue.toString());
                break;
            case 8:
                pp.setSubtotal(aValue.toString());
                break;
            default:
            // Isto não deveria acontecer...             
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    //modifica na linha especificada
    public void setValueAt(ProdutosPPOrc aValue, int rowIndex) {
        ProdutosPPOrc pp = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

        pp.setID(aValue.getID());
        pp.setProduto(aValue.getProduto());
        pp.setDescricao(aValue.getDescricao());
        pp.setCodb(aValue.getCodb());
        pp.setSerial(aValue.getSerial());
        pp.setMarca(aValue.getMarca());
        pp.setPreco(aValue.getPreco());
        pp.setQtde(aValue.getQtde());
        pp.setSubtotal(aValue.getSubtotal());

        fireTableCellUpdated(rowIndex, 0);
        fireTableCellUpdated(rowIndex, 1);
        fireTableCellUpdated(rowIndex, 2);
        fireTableCellUpdated(rowIndex, 3);
        fireTableCellUpdated(rowIndex, 4);
        fireTableCellUpdated(rowIndex, 5);
        fireTableCellUpdated(rowIndex, 6);
        fireTableCellUpdated(rowIndex, 7);
        fireTableCellUpdated(rowIndex, 8);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public ProdutosPPOrc getProdutos(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void addProdutosPP(ProdutosPPOrc f) {
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
    public void addLista(List<ProdutosPPOrc> f) {
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
