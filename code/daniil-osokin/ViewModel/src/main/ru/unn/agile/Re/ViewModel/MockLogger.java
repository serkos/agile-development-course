package ru.unn.agile.Re.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockLogger implements ILogger
{
    private List<LogEntry> logList = new ArrayList<LogEntry>();

    @Override
    public void i(String tag, String text)
    {
        logList.add(new LogEntry(INFO, new Date(), tag, text));
    }

    @Override
    public void w(String tag, String text)
    {
        logList.add(new LogEntry(WARN, new Date(), tag, text));
    }

    @Override
    public void e(String tag, String text)
    {
        logList.add(new LogEntry(ERROR, new Date(), tag, text));
    }

    @Override
    public List<LogEntry> getLog()
    {
        return logList;
    }
}
