package com.irunninglog.dashboard.impl;

import com.irunninglog.Progress;
import com.irunninglog.dashboard.ProgressInfo;
import com.irunninglog.data.impl.IShoeEntityRepository;
import com.irunninglog.data.impl.ShoeEntity;
import com.irunninglog.date.impl.DateService;
import com.irunninglog.math.impl.MathService;
import com.irunninglog.profile.impl.ProfileEntity;
import com.irunninglog.workout.impl.IWorkoutEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DashboardShoesService {

    private final IShoeEntityRepository shoeEntityRepository;
    private final IWorkoutEntityRepository workoutEntityRepository;
    private final MathService mathService;
    private final DateService dateService;

    @Autowired
    public DashboardShoesService(IShoeEntityRepository shoeEntityRepository,
                                 IWorkoutEntityRepository workoutEntityRepository,
                                 MathService mathService,
                                 DateService dateService) {
        super();

        this.shoeEntityRepository = shoeEntityRepository;
        this.workoutEntityRepository = workoutEntityRepository;
        this.mathService = mathService;
        this.dateService = dateService;
    }

    Collection<ProgressInfo> shoes(ProfileEntity profile) {
        List<ProgressInfo> progressList = new ArrayList<>();

        for (ShoeEntity shoeEntity : shoeEntityRepository.dashboardShoes(profile.getId())) {
            BigDecimal distance = workoutEntityRepository.shoeMileage(profile.getId(), shoeEntity.getId());
            if (distance == null) {
                distance = BigDecimal.ZERO;
            }

            BigDecimal target = new BigDecimal(shoeEntity.getMax());

            Progress progress = mathService.progress(distance, target, Boolean.TRUE);

            ProgressInfo progressInfo = new ProgressInfo()
                    .setTitle(shoeEntity.getName())
                    .setSubTitle(shoeEntity.getDescription())
                    .setTextOne(formatStart(shoeEntity))
                    .setTextTwo(mathService.formatProgressText(distance, target, profile.getPreferredUnits()))
                    .setMax(target.intValue())
                    .setValue(Math.min(target.intValue(), distance.intValue()))
                    .setProgress(progress);

            progressList.add(progressInfo);
        }

        return progressList;
    }

    private String formatStart(ShoeEntity shoeEntity) {
        return shoeEntity.getStartDate() == null ? null : "Started " + dateService.formatMedium(shoeEntity.getStartDate());
    }

}