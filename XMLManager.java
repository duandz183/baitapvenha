package wshdk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

public class XMLManager extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfTagName, tfTagValue;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea textArea;

    public XMLManager() {
        setTitle("Quản lý XML - Java Swing");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        tfTagName = new JTextField();
        tfTagValue = new JTextField();
        inputPanel.add(new JLabel("Tên thẻ:"));
        inputPanel.add(tfTagName);
        inputPanel.add(new JLabel("Giá trị thẻ:"));
        inputPanel.add(tfTagValue);

        // Bảng hiển thị danh sách
        tableModel = new DefaultTableModel(new Object[]{"Tên thẻ", "Giá trị"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Các nút chức năng
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnWrite = new JButton("Ghi XML");
        JButton btnRead = new JButton("Đọc XML");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnWrite);
        buttonPanel.add(btnRead);

        // TextArea hiển thị nội dung XML
        textArea = new JTextArea(10, 50);
        textArea.setEditable(false);
        JScrollPane xmlScroll = new JScrollPane(textArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(xmlScroll, BorderLayout.EAST);

        // Các sự kiện nút
        btnAdd.addActionListener(e -> {
            String tag = tfTagName.getText();
            String value = tfTagValue.getText();
            if (!tag.isEmpty() && !value.isEmpty()) {
                tableModel.addRow(new Object[]{tag, value});
                tfTagName.setText("");
                tfTagValue.setText("");
            }
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tableModel.setValueAt(tfTagName.getText(), row, 0);
                tableModel.setValueAt(tfTagValue.getText(), row, 1);
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tableModel.removeRow(row);
            }
        });

        btnWrite.addActionListener(e -> writeXML());

        btnRead.addActionListener(e -> readXML());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                tfTagName.setText(tableModel.getValueAt(row, 0).toString());
                tfTagValue.setText(tableModel.getValueAt(row, 1).toString());
            }
        });
    }

    private void writeXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("DanhSach");
            doc.appendChild(root);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String tag = tableModel.getValueAt(i, 0).toString();
                String value = tableModel.getValueAt(i, 1).toString();

                Element elem = doc.createElement(tag);
                elem.appendChild(doc.createTextNode(value));
                root.appendChild(elem);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data.xml"));
            transformer.transform(source, result);

            JOptionPane.showMessageDialog(this, "Ghi file XML thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readXML() {
        try {
            File file = new File("data.xml");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "File data.xml chưa tồn tại!");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList list = root.getChildNodes();

            textArea.setText("");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node instanceof Element) {
                    textArea.append("<" + node.getNodeName() + ">");
                    textArea.append(node.getTextContent());
                    textArea.append("</" + node.getNodeName() + ">\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new XMLManager().setVisible(true));
    }
}
