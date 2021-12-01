package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    public Button btnStartStop;
    @FXML
    public Button btnResetReszido;
    @FXML
    public Label stopperFelirat;
    @FXML
    public ListView<String> reszidoLista;
    private boolean futE;
    private Timer timer;
    private LocalDateTime startTimer;

    @FXML
    public void initialize() {
        futE = false;
    }

    private void stopperStop() {
        futE = false;
        btnStartStop.setText("Start");
        btnResetReszido.setText("Reset");
        timer.cancel();
    }

    private void stopperStart() {
        btnStartStop.setText("Stop");
        btnResetReszido.setText("Reszido");
        futE = true;
        timer = new Timer();
        startTimer = LocalDateTime.now();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Duration elteltIdo = Duration.between(startTimer, LocalDateTime.now());
                int perc = elteltIdo.toMinutesPart();
                int masodperc = elteltIdo.toSecondsPart();
                int ezredmasodperc = elteltIdo.toMillisPart();
                Platform.runLater(() -> stopperFelirat.setText(String.format("%02d:%02d.%03d", perc, masodperc, ezredmasodperc)));
            }
        };
        timer.schedule(task, 1, 1);
    }

    private void reszidoSzamit() {
        reszidoLista.getItems().add(stopperFelirat.getText());
    }

    private void resetReszido() {
        stopperFelirat.setText("00:00.000");
        reszidoLista.getItems().clear();
    }

    @FXML
    public void startStopClick() {
        if (futE) {
            stopperStop();
        } else {
            stopperStart();
        }
    }

    @FXML
    public void resetReszidoClick() {
        if (futE) {
            reszidoSzamit();
        } else {
            resetReszido();
        }
    }
}