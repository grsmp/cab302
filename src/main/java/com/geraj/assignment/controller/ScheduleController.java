package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;

public class ScheduleController {

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthYearLabel;

    private YearMonth currentMonth;

    // Demo data — swap this out for a real data source (e.g. a TaskRepository / database query)
    private final Map<LocalDate, List<String>> scheduledTasks = new HashMap<>();

    @FXML
    public void initialize() {
        currentMonth = YearMonth.now();
        seedSampleTasks();
        renderCalendar();
    }

    private void seedSampleTasks() {
        LocalDate today = LocalDate.now();
        scheduledTasks.put(today, new ArrayList<>(List.of("Water tomatoes")));
        scheduledTasks.put(today.plusDays(2), new ArrayList<>(List.of("Feed roses")));
        scheduledTasks.put(today.plusDays(5), new ArrayList<>(List.of("Prune hedges", "Check irrigation")));
        scheduledTasks.put(today.minusDays(3), new ArrayList<>(List.of("Turn compost")));
    }

    @FXML
    private void onPreviousMonth() {
        currentMonth = currentMonth.minusMonths(1);
        renderCalendar();
    }

    @FXML
    private void onNextMonth() {
        currentMonth = currentMonth.plusMonths(1);
        renderCalendar();
    }

    @FXML
    private void onAddTask() {
        // TODO: replace
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Task");
        alert.setHeaderText(null);
        alert.setContentText("add-task dialog.");
        alert.showAndWait();
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "main-view.fxml"
        );
    }

    private void renderCalendar() {
        // Keep the MON..SUN header row (row 0), clear everything below it
        calendarGrid.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });

        monthYearLabel.setText(currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                + " " + currentMonth.getYear());

        LocalDate firstOfMonth = currentMonth.atDay(1);
        LocalDate today = LocalDate.now();

        // Monday-based column: Monday = 0 ... Sunday = 6
        int startColumn = firstOfMonth.getDayOfWeek().getValue() - 1;
        LocalDate gridStart = firstOfMonth.minusDays(startColumn);

        for (int cell = 0; cell < 42; cell++) {
            LocalDate date = gridStart.plusDays(cell);
            int row = 1 + (cell / 7);
            int column = cell % 7;

            boolean inCurrentMonth = YearMonth.from(date).equals(currentMonth);
            boolean isToday = date.equals(today);

            VBox dayCell = buildDayCell(date, inCurrentMonth, isToday);
            GridPane.setRowIndex(dayCell, row);
            GridPane.setColumnIndex(dayCell, column);
            calendarGrid.getChildren().add(dayCell);
        }
    }

    private VBox buildDayCell(LocalDate date, boolean inCurrentMonth, boolean isToday) {
        VBox cell = new VBox(4);
        cell.setAlignment(Pos.TOP_CENTER);
        cell.setPrefSize(110, 90);
        cell.setPadding(new Insets(8));

        String background = isToday ? "#1E1E1E" : "white";
        String border = inCurrentMonth ? "#E5E3DD" : "#F2F1ED";
        cell.setStyle("-fx-background-color: " + background
                + "; -fx-border-color: " + border
                + "; -fx-border-radius: 8; -fx-background-radius: 8;");

        Label dayLabel = new Label(String.valueOf(date.getDayOfMonth()));
        String textColor = isToday ? "white" : (inCurrentMonth ? "#1E1E1E" : "#C9C7C0");
        dayLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: " + textColor + ";");
        cell.getChildren().add(dayLabel);

        List<String> tasks = scheduledTasks.getOrDefault(date, Collections.emptyList());
        int shown = 0;
        for (String task : tasks) {
            if (shown >= 2) break;
            Label taskLabel = new Label(task);
            taskLabel.setWrapText(true);
            taskLabel.setMaxWidth(96);
            taskLabel.setStyle("-fx-font-size: 10; -fx-text-fill: " + (isToday ? "white" : "#5C5A54")
                    + "; -fx-background-color: " + (isToday ? "rgba(255,255,255,0.15)" : "#F0EFE9")
                    + "; -fx-background-radius: 4; -fx-padding: 2 4 2 4;");
            cell.getChildren().add(taskLabel);
            shown++;
        }
        if (tasks.size() > shown) {
            Label more = new Label("+" + (tasks.size() - shown) + " more");
            more.setStyle("-fx-font-size: 9; -fx-text-fill: " + (isToday ? "#D6D6D6" : "#A6A49C") + ";");
            cell.getChildren().add(more);
        }

        cell.setOnMouseClicked(event -> onDaySelected(date, tasks));
        return cell;
    }

    private void onDaySelected(LocalDate date, List<String> tasks) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", "
                + date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getDayOfMonth());
        alert.setContentText(tasks.isEmpty() ? "No tasks scheduled for this day." : String.join("\n", tasks));
        alert.showAndWait();
    }
}