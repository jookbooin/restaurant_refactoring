package com.restaurant.reservation.web.form.validation;

import javax.validation.GroupSequence;

import static com.restaurant.reservation.web.form.validation.ValidationGroups.*;

@GroupSequence({NotEmptyGroup.class, PatternGroup.class,EmailGroup.class})
public interface ValidationSequence {
}
