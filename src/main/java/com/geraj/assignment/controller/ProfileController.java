package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.dao.IContributionDAO;
import com.geraj.assignment.dao.SqliteAccountDAO;
import com.geraj.assignment.dao.StubContributionDAO;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.AccountValidator;
import com.geraj.assignment.model.ContributionSummary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Controls the Account Profile screen.
 */
public class ProfileController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label cropsContributedLabel;

    @FXML
    private Label wateringShiftsDoneLabel;

    @FXML
    private Label harvestsLoggedLabel;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Label firstNameErrorLabel;

    @FXML
    private Label lastNameErrorLabel;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label phoneNumberErrorLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label unsavedChangesLabel;

    @FXML
    private ListView<String> gardenMembershipsListView;

    private final IAccountDAO accountDAO;
    private final IContributionDAO contributionDAO;
    private Account account;
    private String savedFirstName = "";
    private String savedLastName = "";
    private String savedEmail = "";
    private String savedPhoneNumber = "";

    /**
     * Creates a controller using the application DAOs.
     */
    public ProfileController() {
        this(new SqliteAccountDAO(), new StubContributionDAO());
    }

    /**
     * Creates a controller using supplied DAOs.
     *
     * @param accountDAO the account persistence service
     * @param contributionDAO the contribution-summary service
     */
    public ProfileController(
            IAccountDAO accountDAO,
            IContributionDAO contributionDAO
    ) {
        this.accountDAO = Objects.requireNonNull(
                accountDAO,
                "Account DAO cannot be null"
        );
        this.contributionDAO = Objects.requireNonNull(
                contributionDAO,
                "Contribution DAO cannot be null"
        );
    }

    @FXML
    private void initialize() {
        firstNameTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> updateDirtyDisplay()
        );
        lastNameTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> updateDirtyDisplay()
        );
        emailTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> updateDirtyDisplay()
        );
        phoneNumberTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> updateDirtyDisplay()
        );
        loadProfile();
    }

    /**
     * Loads the signed-in account and contribution totals into the screen.
     */
    public void loadProfile() {
        AccountSession session = AccountSession.getInstance();
        if (session == null) {
            messageLabel.setText("No signed-in account is available.");
            return;
        }

        account = session.getAccount();
        usernameLabel.setText(account.getName());
        firstNameTextField.setText(account.getFirstName());
        lastNameTextField.setText(account.getLastName());
        emailTextField.setText(account.getEmail());
        phoneNumberTextField.setText(account.getPhoneNumber());

        ContributionSummary summary = contributionDAO.getContributionSummary(account);
        cropsContributedLabel.setText(String.valueOf(summary.getCropsContributed()));
        wateringShiftsDoneLabel.setText(String.valueOf(summary.getWateringShiftsDone()));
        harvestsLoggedLabel.setText(String.valueOf(summary.getHarvestsLogged()));

        saveCurrentValues();
        clearErrors();
        messageLabel.setText("");
        updateDirtyDisplay();
    }

    /**
     * Validates both editable name fields and displays their errors.
     *
     * @return true when both names are valid
     */
    public boolean validateName() {
        AccountValidator.ValidationResult result = validateFields();
        Map<String, String> errors = result.getErrors();
        firstNameErrorLabel.setText(errors.getOrDefault("firstName", ""));
        lastNameErrorLabel.setText(errors.getOrDefault("lastName", ""));
        return !result.hasError("firstName") && !result.hasError("lastName");
    }

    /**
     * Validates the editable email field and displays its error.
     *
     * @return true when the email is valid
     */
    public boolean validateEmail() {
        AccountValidator.ValidationResult result = validateFields();
        emailErrorLabel.setText(
                result.getErrors().getOrDefault("email", "")
        );
        return !result.hasError("email");
    }

    /**
     * Validates the editable phone field and displays its error.
     *
     * @return true when the phone number is valid
     */
    public boolean validatePhone() {
        AccountValidator.ValidationResult result = validateFields();
        phoneNumberErrorLabel.setText(
                result.getErrors().getOrDefault("phoneNumber", "")
        );
        return !result.hasError("phoneNumber");
    }

    /**
     * Validates and persists all editable personal information.
     */
    @FXML
    public void saveChanges() {
        clearErrors();
        AccountValidator.ValidationResult result = validateFields();
        displayErrors(result);

        if (!result.isValid() || account == null) {
            messageLabel.setText("Correct the highlighted fields before saving.");
            return;
        }

        Account updatedAccount = new Account(
                account.getName(),
                emailTextField.getText().trim(),
                firstNameTextField.getText().trim(),
                lastNameTextField.getText().trim(),
                phoneNumberTextField.getText().replace(" ", ""),
                account.getHash()
        );

        if (!accountDAO.updatePersonalInformation(updatedAccount)) {
            messageLabel.setText("Your changes could not be saved.");
            return;
        }

        account.setFirstName(updatedAccount.getFirstName());
        account.setLastName(updatedAccount.getLastName());
        account.setEmail(updatedAccount.getEmail());
        account.setPhoneNumber(updatedAccount.getPhoneNumber());
        firstNameTextField.setText(account.getFirstName());
        lastNameTextField.setText(account.getLastName());
        emailTextField.setText(account.getEmail());
        phoneNumberTextField.setText(account.getPhoneNumber());
        saveCurrentValues();
        messageLabel.setText("Your profile changes were saved.");
        updateDirtyDisplay();
    }

    /**
     * Restores the last saved personal information.
     */
    @FXML
    public void cancelChanges() {
        firstNameTextField.setText(savedFirstName);
        lastNameTextField.setText(savedLastName);
        emailTextField.setText(savedEmail);
        phoneNumberTextField.setText(savedPhoneNumber);
        clearErrors();
        messageLabel.setText("");
        updateDirtyDisplay();
    }

    /**
     * Reports whether any editable field differs from its saved value.
     *
     * @return true when the screen contains unsaved changes
     */
    public boolean hasUnsavedChanges() {
        return !firstNameTextField.getText().equals(savedFirstName)
                || !lastNameTextField.getText().equals(savedLastName)
                || !emailTextField.getText().equals(savedEmail)
                || !phoneNumberTextField.getText().equals(savedPhoneNumber);
    }

    /**
     * Requests confirmation before navigating away from unsaved changes.
     *
     * @return true when navigation may continue
     */
    public boolean confirmNavigationIfDirty() {
        if (!hasUnsavedChanges()) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved profile changes");
        alert.setHeaderText("Leave without saving your changes?");
        alert.setContentText("Your unsaved Account Profile changes will be discarded.");
        Optional<ButtonType> response = alert.showAndWait();
        return response.isPresent() && response.get() == ButtonType.OK;
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        if (confirmNavigationIfDirty()) {
            SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
        }
    }

    private AccountValidator.ValidationResult validateFields() {
        return AccountValidator.validatePersonalInformation(
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                emailTextField.getText(),
                phoneNumberTextField.getText()
        );
    }

    private void displayErrors(AccountValidator.ValidationResult result) {
        Map<String, String> errors = result.getErrors();
        firstNameErrorLabel.setText(errors.getOrDefault("firstName", ""));
        lastNameErrorLabel.setText(errors.getOrDefault("lastName", ""));
        emailErrorLabel.setText(errors.getOrDefault("email", ""));
        phoneNumberErrorLabel.setText(errors.getOrDefault("phoneNumber", ""));
    }

    private void clearErrors() {
        firstNameErrorLabel.setText("");
        lastNameErrorLabel.setText("");
        emailErrorLabel.setText("");
        phoneNumberErrorLabel.setText("");
    }

    private void saveCurrentValues() {
        savedFirstName = firstNameTextField.getText();
        savedLastName = lastNameTextField.getText();
        savedEmail = emailTextField.getText();
        savedPhoneNumber = phoneNumberTextField.getText();
    }

    private void updateDirtyDisplay() {
        unsavedChangesLabel.setText(
                hasUnsavedChanges() ? "Unsaved changes" : ""
        );
    }
}
