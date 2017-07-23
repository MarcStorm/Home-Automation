package COURSE02148.homeautomation.clients.locksystem.view;

import COURSE02148.homeautomation.clients.locksystem.LockSystemClient;
import COURSE02148.homeautomation.clients.locksystem.LockSystemCluster;

import javax.swing.*;
import java.awt.*;

public class LockSystemView extends JFrame {

    private LockSystemCluster lockSystemCluster;

    public LockSystemView(LockSystemCluster lockSystemCluster) {
        super("Lock System GUI");
        this.lockSystemCluster = lockSystemCluster;

        // Set window parameters
        this.setPreferredSize(new Dimension(600, 600));
        this.setMinimumSize(new Dimension(500, 500));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(true);

        this.getContentPane().add(new LockSystemPanel(), BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public class LockSystemPanel extends JPanel{

        public LockSystemPanel() {
            GridLayout gridLayout = new GridLayout(3,3);
            this.setLayout(gridLayout);

            // Add all clients to the view
            for(LockSystemClient client : lockSystemCluster.lockClients){
                LockPanel lockPanel = new LockPanel(client.clientName);
                client.setLockPanel(lockPanel);
                add(lockPanel);
            }

        }
    }

    public class LockPanel extends JPanel {
        public LockPanel(String name) {
            JLabel label = new JLabel(name);
            this.add(label);
            this.setBackground(Color.GREEN);
        }
    }

}
