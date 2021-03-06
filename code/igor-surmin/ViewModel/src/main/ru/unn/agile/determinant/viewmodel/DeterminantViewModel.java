package ru.unn.agile.determinant.viewmodel;

import java.util.List;
import ru.unn.agile.determinant.model.Matrix;
import ru.unn.agile.determinant.model.DeterminantCalculator;

public class DeterminantViewModel {
    private String matrixSize;
    private String matrix;

    public String status;
    public boolean isCalculateButtonEnabled;

    private ILogger logger;
    private boolean isInputChanged;

    public DeterminantViewModel(ILogger logger)
    {
        if (logger == null)
            throw new IllegalArgumentException("Logger object is null");
        status = Status.WAITING;
        matrixSize = "";
        matrix = "";
        isCalculateButtonEnabled = false;
        this.logger = logger;
    }

    public String getMatrixSize() {
        return matrixSize;
    }

    public void setMatrixSize(String matrixSize) {
        if (matrixSize.equals(this.matrixSize))
            return;
        this.matrixSize = matrixSize;
        isInputChanged = true;
    }

    public String getMatrix() {
        return matrix;
    }

    public void setMatrix(String matrix) {
        if (parseInput() && this.matrix.equals(matrix))
            return;
        this.matrix = matrix;
        isInputChanged = true;
    }

    public String getStatus() {
        return status;
    }

    public String formatMatrix(String matrix)
    {
        return "[" + matrix.trim().replace(' ', ',').replace('\n', ',') + "]";
    }

    public String getInputReadyLogMessage()
    {
        String message = LogMessages.READY_MESSAGE + "matrix size = " + matrixSize
            + ", matrix = " + formatMatrix(matrix);
        return message;
    }

    private boolean parseInput() {
        int n;
        try {
            if (!matrixSize.isEmpty())
                n = Integer.parseInt(matrixSize);
            else {
                status = Status.WAITING;
                isCalculateButtonEnabled = false;
                return false;
            }
        }
        catch (Exception e) {
            status = Status.INCORRECT_NUMBER;
            isCalculateButtonEnabled = false;
            return false;
        }

        if (n <= 0) {
            status = Status.INCORRECT_NUMBER;
            isCalculateButtonEnabled = false;
            return false;
        }

        if (matrix.isEmpty()) {
            status = Status.MORE_ROWS;
            isCalculateButtonEnabled = false;
            return false;
        }

        String[] rows = matrix.split("\n");
        if (rows.length < n) {
            status =  Status.MORE_ROWS;
            isCalculateButtonEnabled = false;
            return false;
        }

        if (rows.length > n) {
            status = Status.LESS_ROWS;
            isCalculateButtonEnabled = false;
            return false;
        }

        for (int i = 0; i < n; i++) {
            String[] items = rows[i].split(" ");
            if (items.length > n) {
                status = Status.LESS_COLUMNS + Integer.toString(i);
                isCalculateButtonEnabled = false;
                return false;
            }

            if (items.length < n) {
                status = Status.MORE_COLUMNS + Integer.toString(i);
                isCalculateButtonEnabled = false;
                return false;
            }

            for (int j = 0; j < n; j++) {
                try {
                    Double.parseDouble(items[j]);
                }
                catch (Exception e) {
                    status = Status.INCORRECT_ITEM + "(" + Integer.toString(i + 1) + ", " + Integer.toString(j + 1) + ")";
                    isCalculateButtonEnabled = false;
                    return false;
                }
            }
        }

        status = Status.READY;
        isCalculateButtonEnabled = true;
        return true;
    }

    public void handleKey() {
        logInput();
        parseInput();
    }

    private String getCalculateLogMessage()
    {
        String message = LogMessages.CALCULATE_MESSAGE + "matrix size = " + matrixSize
                + ", matrix = " + formatMatrix(matrix);
        return message;
    }

    private void logInput() {
        if (!isInputChanged || !parseInput())
            return;
        logger.logMessage(getInputReadyLogMessage());
    }

    private void logCalculating() {
        if (!isInputChanged)
            return;
        logger.logMessage(getCalculateLogMessage());
        isInputChanged = false;
    }

    public void Calculate()
    {
        logCalculating();
        if (!parseInput())
            return;

        int n = Integer.parseInt(matrixSize);
        Matrix A = new Matrix(n);
        String[] items = matrix.split("[\n ]");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            {
                double val = Double.parseDouble(items[i * n + j]);
                A.setItem(i, j, val);
            }
        DeterminantCalculator determinantCalculator = new DeterminantCalculator(A);
        status = "Determinant = " + determinantCalculator.getDeterminant();
    }

    public List<String> getLog() {
        List<String> log = logger.getLog();
        return log;
    }

    public void clearLog() {
        logger.clearLog();
    }

    public class Status {
        public static final String WAITING = "Please provide input data";
        public static final String READY = "Press 'Calculate'";
        public static final String INCORRECT_NUMBER = "Incorrect number of items";
        public static final String INCORRECT_ITEM = "Syntax error at item ";
        public static final String MORE_ROWS = "Too low rows";
        public static final String LESS_ROWS = "Too much rows";
        public static final String MORE_COLUMNS = "Too low columns in row ";
        public static final String LESS_COLUMNS = "Too much columns in row ";
    }

    public class LogMessages {
        public static final String CALCULATE_MESSAGE = "Calculating determinant. ";
        public static final String READY_MESSAGE = "Input data ready. ";
    }
}
