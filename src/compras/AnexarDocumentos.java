/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package compras;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.xhtmlrenderer.util.ImageUtil;

/**
 *
 * @author hugog
 */
public class AnexarDocumentos extends javax.swing.JPanel {

    private JButton selectButton;
    private JPanel photoPanel;
    private ArrayList<ImageIcon> selectedThumbnails;
    private ArrayList<ImageIcon> selectedImages;
    private ArrayList<JLabel> thumbnailLabels;
    private ArrayList<JLabel> imageLabels;
    private JLabel countLabel;

    public AnexarDocumentos(int step) {
        initComponents();
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        selectButton = new JButton("Upload");
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectPhotos();
            }
        });

        countLabel = new JLabel("Nenhuma foto selecionada");
        ImageIcon icon = new ImageIcon(getClass().getResource("/src/upload-de-arquivo.png"));
        selectButton.setIcon(icon);
        panel.add(selectButton);
        panel.add(countLabel);

        photoPanel = new JPanel();
        photoPanel.setLayout(new GridLayout(3, 3, 10, 10)); // 2 rows, 3 columns, 10 pixels of horizontal and vertical gap
        photoPanel.setPreferredSize(new Dimension(700, 300));
        photoPanel.setBackground(new Color(218, 218, 218));

        add(panel, BorderLayout.NORTH);
        add(photoPanel, BorderLayout.CENTER);
        
        selectedThumbnails = new ArrayList<>();
        selectedImages = new ArrayList<>();
        thumbnailLabels = new ArrayList<>();
        imageLabels = new ArrayList<>();
    }

    private void selectPhotos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            selectedThumbnails.clear();
            selectedImages.clear();
            thumbnailLabels.clear();
            imageLabels.clear();

            for (File file : selectedFiles) {
                if (selectedThumbnails.size() >= 6) {
                    break;
                }

                try {
                    // Carrega a imagem original
                    ImageIcon imageIcon = new ImageIcon(file.getPath());
                    selectedImages.add(imageIcon);

                    // Cria a miniatura redimensionando a imagem
                    Image thumbnailImage = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    ImageIcon thumbnailIcon = new ImageIcon(thumbnailImage);
                    selectedThumbnails.add(thumbnailIcon);

                    // Cria JLabel para a miniatura
                    JLabel thumbnailLabel = new JLabel();
                    thumbnailLabel.setIcon(thumbnailIcon);
                    thumbnailLabels.add(thumbnailLabel);

                    // Cria JLabel para a imagem original (não visível)
                    JLabel imageLabel = new JLabel();
                    imageLabel.setIcon(imageIcon);
                    imageLabels.add(imageLabel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            displaySelectedPhotos();
        }
    }

    private void displaySelectedPhotos() {
        photoPanel.removeAll();

        for (int i = 0; i < selectedThumbnails.size(); i++) {
            ImageIcon thumbnailIcon = selectedThumbnails.get(i);

            JLabel thumbnailLabel = thumbnailLabels.get(i);

            // Adicionar margens nas miniaturas
            thumbnailLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JButton deleteButton = new JButton("Delete");
            final int index = i; // Índice da imagem correspondente ao botão de exclusão
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    removeSelectedPhoto(index);
                }
            });

            JPanel thumbnailPanelWithDelete = new JPanel(new BorderLayout());
            thumbnailPanelWithDelete.add(thumbnailLabel, BorderLayout.CENTER);
            thumbnailPanelWithDelete.add(deleteButton, BorderLayout.SOUTH);

            photoPanel.add(thumbnailPanelWithDelete);
        }

        countLabel.setText(selectedThumbnails.size() + " photos selected");
        photoPanel.revalidate();
        photoPanel.repaint();
    }

    private void removeSelectedPhoto(int index) {
        if (index >= 0 && index < selectedThumbnails.size()) {
            selectedThumbnails.remove(index);
            selectedImages.remove(index);
            JLabel thumbnailLabel = thumbnailLabels.remove(index);
            JLabel imageLabel = imageLabels.remove(index);
            photoPanel.remove(thumbnailLabel);
            photoPanel.remove(imageLabel);
            displaySelectedPhotos();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
