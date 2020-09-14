package com.accusterltapp.adapter;

import com.accusterltapp.model.RegisterPatient;

import java.util.Comparator;

/**
 * A collection of {@link Comparator}s for {@link RegisterPatient} objects.
 *
 */
public final class PatientComparators {

    private PatientComparators() {
        //no instance
    }

    public static Comparator<RegisterPatient> getPatientLabelIdComparator() {
        return new PatientLabelIdComparator();
    }

    public static Comparator<RegisterPatient> getPatientNameComparator() {
        return new PatientNameComparator();
    }

    public static Comparator<RegisterPatient> getPatientGenderComparator() {
        return new PatientGenderComparator();
    }

    public static Comparator<RegisterPatient> getPatientTestComparator() {
        return new PatientTestComparator();
    }


    private static class PatientLabelIdComparator implements Comparator<RegisterPatient> {

        @Override
        public int compare(final RegisterPatient patient1, final RegisterPatient patient2) {
            return patient1.getpLabelId().compareTo(patient2.getpLabelId());
        }
    }

    private static class PatientNameComparator implements Comparator<RegisterPatient> {

        @Override
        public int compare(final RegisterPatient patient1, final RegisterPatient patient2) {
            return patient1.getUserregistration_complete_name().compareTo(patient2.getUserregistration_complete_name());
        }
    }

    private static class PatientGenderComparator implements Comparator<RegisterPatient> {

        @Override
        public int compare(final RegisterPatient patient1, final RegisterPatient patient2) {
            return patient1.getUserregistration_gender_id().compareTo(patient2.getUserregistration_gender_id());
        }
    }

    private static class PatientTestComparator implements Comparator<RegisterPatient> {

        @Override
        public int compare(final RegisterPatient patient1, final RegisterPatient patient2) {
            return patient1.getUserregistration_org_id().compareTo(patient2.getUserregistration_org_id());
        
        }
    }

}
