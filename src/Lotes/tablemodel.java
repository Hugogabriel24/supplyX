package Lotes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hugogabriel
 */
public class tablemodel extends AbstractTableModel {

    private List<itensGetSetters> linhas;
    private String[] colunas = new String[]{"Tipo", "Descricao", "Serial", "Marca", "Modelo", "Patrimonio", "Status", "Preco", "QTDE", "Obs"};

    public tablemodel() {
        linhas = new ArrayList<itensGetSetters>();
    }

    public tablemodel(List<itensGetSetters> lista) {
        linhas = new ArrayList<itensGetSetters>(lista);
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
        itensGetSetters pp = linhas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return pp.getTipo();
            case 1:
                return pp.getDescricao();
            case 2:
                return pp.getSerial();
            case 3:
                return pp.getMarca();
            case 4:
                return pp.getModelo();
            case 5:
                return pp.getPatrimonio();
            case 6:
                return pp.getStatus();
            case 7:
                return pp.getPreco();
            case 8:
                return pp.getQuantidade();
            case 9:
                return pp.getObs();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    //modifica na linha e coluna especificada
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        itensGetSetters pp = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

        switch (columnIndex) { // Seta o valor do campo respectivo
            case 0:
                pp.setTipo(aValue.toString());
                break;
            case 1:
                pp.setDescricao(aValue.toString());
                break;
            case 2:
                pp.setSerial(aValue.toString());
                break;
            case 3:
                pp.setMarca(aValue.toString());
                break;
            case 4:
                pp.setModelo(aValue.toString());
                break;
            case 5:
                pp.setPatrimonio(aValue.toString());
                break;
            case 6:
                pp.setStatus(aValue.toString());
                break;
            case 7:
                pp.setPreco(aValue.toString());
                break;
            case 8:
                pp.setQuantidade(aValue.toString());
                break;
            case 9:
                pp.setObs(aValue.toString());
                break;
            default:
            // Isto não deveria acontecer...             
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    //modifica na linha especificada
    public void setValueAt(itensGetSetters aValue, int rowIndex) {
        itensGetSetters pp = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

        pp.setTipo(aValue.getTipo());
        pp.setDescricao(aValue.getDescricao());
        pp.setSerial(aValue.getSerial());
        pp.setMarca(aValue.getMarca());
        pp.setModelo(aValue.getModelo());
        pp.setPatrimonio(aValue.getPatrimonio());
        pp.setStatus(aValue.getStatus());
        pp.setPreco(aValue.getPreco());
        pp.setQuantidade(aValue.getQuantidade());
        pp.setObs(aValue.getObs());

        fireTableCellUpdated(rowIndex, 0);
        fireTableCellUpdated(rowIndex, 1);
        fireTableCellUpdated(rowIndex, 2);
        fireTableCellUpdated(rowIndex, 3);
        fireTableCellUpdated(rowIndex, 4);
        fireTableCellUpdated(rowIndex, 5);
        fireTableCellUpdated(rowIndex, 6);
        fireTableCellUpdated(rowIndex, 7);
        fireTableCellUpdated(rowIndex, 8);
        fireTableCellUpdated(rowIndex, 9);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public itensGetSetters getProdutos(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void addProdutosPP(itensGetSetters f) {
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
    public void addLista(List<itensGetSetters> f) {
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
