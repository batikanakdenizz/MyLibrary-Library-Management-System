
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author batik
 */
public class DisplayBookCoverage extends javax.swing.JFrame {

    /**
     * Creates new form DisplayBookCoverage
     */
    public DisplayBookCoverage() {
        initComponents();
        setLocationRelativeTo(null); 
    }
    
    private int userType;

    public DisplayBookCoverage(int userType) {
    this.userType = userType;
    initComponents();
    setLocationRelativeTo(null);
}

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bookid_label = new javax.swing.JLabel();
        bookId_tf = new javax.swing.JTextField();
        coverLabel = new javax.swing.JLabel();
        showCover_button = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bookid_label.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bookid_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        bookid_label.setText("ENTER A BOOK ID :");
        bookid_label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        bookId_tf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bookId_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookId_tfActionPerformed(evt);
            }
        });

        coverLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        showCover_button.setText("SHOW");
        showCover_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showCover_buttonActionPerformed(evt);
            }
        });

        jToggleButton1.setText("BACK TO MAIN MENU");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(bookid_label, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(bookId_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(showCover_button, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jToggleButton1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(coverLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bookid_label, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bookId_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(showCover_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coverLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bookId_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookId_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookId_tfActionPerformed

    private void showCover_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showCover_buttonActionPerformed
     
    try {
        int bookId = Integer.parseInt(bookId_tf.getText().trim());

        DataBase db = new DataBase();
        Book book = db.getBookById(bookId);

        if (book != null) {
            String imagePath = book.getCover(); // örnek: images/Book1.jpg

            // 🔽 ImageIcon kullanarak resmi yükle
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH); // boyut ayarla
            coverLabel.setIcon(new ImageIcon(image));
        } else {
            JOptionPane.showMessageDialog(this, "No book found with ID: " + bookId);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid Book ID.");
    }

    }//GEN-LAST:event_showCover_buttonActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed

       this.setVisible(false);
       return;
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DisplayBookCoverage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayBookCoverage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayBookCoverage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayBookCoverage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DisplayBookCoverage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bookId_tf;
    private javax.swing.JLabel bookid_label;
    private javax.swing.JLabel coverLabel;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JButton showCover_button;
    // End of variables declaration//GEN-END:variables
}
