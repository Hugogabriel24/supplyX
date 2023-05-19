/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.tanamao.model;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.TransferHandler;
import static CadastroProdutos.TelaCadPartePecas.Painel1ImagePP;
import static CadastroProdutos.TelaCadPartePecas.viewImage1PP;

/**
 *
 * @author hugog
 */
public class ImageTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.imageFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        Transferable transferable = support.getTransferable();
        try {
            Image image = (Image) transferable.getTransferData(DataFlavor.imageFlavor);
            ImageIcon imagemIcon1 = new ImageIcon(image);
            imagemIcon1.setImage(imagemIcon1.getImage().getScaledInstance(Painel1ImagePP.getWidth(), Painel1ImagePP.getHeight(), 100));
            viewImage1PP.setIcon(imagemIcon1);
            
            return true;
        } catch (UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
