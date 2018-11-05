package org.firstinspires.ftc.teamcode.helper;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoBot extends Robot {

    private GoldAlignDetector detector;
    public AutoBot(HardwareMap hardwareMap, Telemetry tele, GoldAlignDetector d) {
        super(hardwareMap, tele);
        d = detector;

    }

    public void setUpDetector(GoldAlignDetector d){
        d.init(hwm.appContext, CameraViewDisplay.getInstance());
        d.useDefaults();

        d.alignSize = 100;
        d.alignPosOffset = 0;
        d.downscale = 0.4;

        d.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        d.maxAreaScorer.weight = 0.005;

        d.ratioScorer.weight = 5;
        d.ratioScorer.perfectRatio = 1;
        d.enable();
    }
}
