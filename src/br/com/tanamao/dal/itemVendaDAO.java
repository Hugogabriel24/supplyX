/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tanamao.dal;

import TablesGetSetters.ProdutosOutros;
import br.com.tanamao.model.itemVenda;
import ds.desktop.notify.DesktopNotify;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import static Vendas.TelaPagamento.InputTotalPag;

/**
 *
 * @author user
 */
public class itemVendaDAO {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public String GetID() {
        String idvenda = null;
        String sql = "select max(idvendas)idvendas from vendas";
        conexao = ModuloConexao.conector();
        try {

            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String idUV = rs.getString(1);
                idvenda = idUV;

            }
            return idvenda;

        } catch (Exception e) {

        }
        return idvenda;

    }

    public List<ProdutosOutros> readOutros() {
        List<ProdutosOutros> lista = new ArrayList<>();
        conexao = ModuloConexao.conector();
        String sql = "select idpartepecas,descricao,nserie,marca,precovenda,qtde from partepecas order by descricao";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {

                ProdutosOutros PO = new ProdutosOutros();
                PO.setID(rs.getString(1));
                PO.setDescricao(rs.getString(2));
                PO.setSerial(rs.getString(3));
                PO.setMarca(rs.getString(4));
                PO.setPreco(rs.getString(5));
                PO.setQtde(rs.getString(6));

                lista.add(PO);

            }
            return lista;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return lista;
    }

    public void CadastrarItem(itemVenda obj) {

        String sql = "insert into itemvenda(idvenda,idproduto,produto,descricaoprod,codigob,serialprod,marcaprod,precoprod,qtdeprod,subtotal) values(?,?,?,?,?,?,?,?,?,?)";
        conexao = ModuloConexao.conector();

        try {

            String getiduv = GetID();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getiduv);
            pst.setString(2, obj.getIdproduto());
            pst.setString(3, obj.getProduto());
            pst.setString(4, obj.getDescricao());
            pst.setString(5, obj.getCodigob());
            pst.setString(6, obj.getSerial());
            pst.setString(7, obj.getMarca());
            pst.setString(8, obj.getPreco());
            pst.setString(9, obj.getQtd());
            pst.setString(10, obj.getSubtotal());

            if ((InputTotalPag.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {

                }
            }

        } catch (Exception e) {
            System.out.print(e);
            JOptionPane.showMessageDialog(null, "Item não vinculado a venda!!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * *************************************************************************************************************************
     */
    public List<itemVenda> listaItemVenda(String idvenda) {
        List<itemVenda> lista = new ArrayList<>();
        conexao = ModuloConexao.conector();
        String sql = "select iditemvenda,idproduto,produto,descricaoprod,codigob,serialprod,marcaprod,precoprod,qtdeprod,subtotal from itemvenda where idvenda = ?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, idvenda);
            rs = pst.executeQuery();
            while (rs.next()) {

                itemVenda item = new itemVenda();
                item.setIdItemVenda(rs.getString(1));
                item.setIdproduto(rs.getString(2));
                item.setProduto(rs.getString(3));
                item.setDescricao(rs.getString(4));
                item.setCodigob(rs.getString(5));
                item.setSerial(rs.getString(6));
                item.setMarca(rs.getString(7));
                item.setPreco(rs.getString(8));
                item.setQtd(rs.getString(9));
                item.setSubtotal(rs.getString(10));

                lista.add(item);

            }
            return lista;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return lista;
    }


    public void AumentaEstoqueNotebook(String id, int qtdNovo, String Descricao) {

        String sql = "update notebooks set qtde=? where idnotebooks =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
                DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + Descricao + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);

            }

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void AumentaEstoqueOutros(String id, int qtdNovo, String Descricao) {

        String sql = "update partepecas set qtde=? where idpartepecas =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

                DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + Descricao + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);

            }

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void baixaEstoqueOutros(String id, int qtdNovo, String DescricaoOutros) {

        String sql = "update partepecas set qtde=? where idpartepecas =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
                DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + DescricaoOutros + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);

            }

        } catch (Exception e) {

        }

    }

    public void baixaEstoqueNotebook(String id, int qtdNovo, String DescricaoNote) {

        String sql = "update notebooks set qtde=? where idnotebooks =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
                DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + DescricaoNote + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);

            }

        } catch (Exception e) {
        }

    }

    public int retornaEstoqueAtualOutros(String id) {
        String sql = "select qtde from partepecas where idpartepecas=?";
        conexao = ModuloConexao.conector();

        try {

            int qtdE = 0;
            pst = conexao.prepareStatement(sql);
            pst.setString(1, id);

            rs = pst.executeQuery();

            if (rs.next()) {
                qtdE = (rs.getInt(1));
            }
            return qtdE;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public int retornaEstoqueAtualNotebook(String idNote) {
        String sql = "select qtde from notebooks where idnotebooks=?";
        conexao = ModuloConexao.conector();

        try {

            int qtdE = 0;
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idNote);

            rs = pst.executeQuery();

            if (rs.next()) {
                qtdE = (rs.getInt(1));
            }
            return qtdE;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
