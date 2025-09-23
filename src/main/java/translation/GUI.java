package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator("sample.json");
            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

            JComboBox<String> countryBox = new JComboBox<>();
            for (String code : translator.getCountryCodes()) {
                countryBox.addItem(countryCodeConverter.fromCountryCode(code));
            }
            countryPanel.add(countryBox);

            JPanel languagePanel = new JPanel();

            languagePanel.add(new JLabel("Language:"));
            String[] items = new String[translator.getLanguageCodes().size()];
            JComboBox<String> languageComboBox = new JComboBox<>();
            int i = 0;
            for(String langaugeCode : translator.getLanguageCodes()) {
                items[i++] = languageCodeConverter.fromLanguageCode(langaugeCode);
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            languagePanel.add(scrollPane, 1);

            JPanel buttonPanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            countryBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = list.getModel().getElementAt(list.getSelectedIndex());
                    String country = countryBox.getSelectedItem().toString();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator("sample.json");

                    String result = translator.translate(countryCodeConverter.fromCountry(country).toLowerCase(), languageCodeConverter.fromLanguage(language));
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });
            list.addListSelectionListener(new ListSelectionListener(){
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String language = list.getModel().getElementAt(list.getSelectedIndex());
                    String country = countryBox.getSelectedItem().toString();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator("sample.json");

                    String result = translator.translate(countryCodeConverter.fromCountry(country).toLowerCase(), languageCodeConverter.fromLanguage(language));
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
