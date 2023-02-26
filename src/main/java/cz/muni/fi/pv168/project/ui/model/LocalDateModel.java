package cz.muni.fi.pv168.project.ui.model;

import org.jdatepicker.AbstractDateModel;
import org.jdatepicker.DateModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class for using a data model.
 *
 * @author Radomír Dedek
 */
public class LocalDateModel extends AbstractDateModel<LocalDate> implements DateModel<LocalDate> {

    @Override
    protected Calendar toCalendar(LocalDate from) {
        return GregorianCalendar.from(from.atStartOfDay(ZoneId.systemDefault()));
    }

    @Override
    protected LocalDate fromCalendar(Calendar from) {
        return from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
