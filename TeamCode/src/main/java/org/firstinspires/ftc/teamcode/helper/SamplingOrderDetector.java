package org.firstinspires.ftc.teamcode.helper;

import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.DogeCVDetector;
import com.disnodeteam.dogecv.filters.DogeCVColorFilter;
import com.disnodeteam.dogecv.filters.HSVRangeFilter;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.disnodeteam.dogecv.scoring.MaxAreaScorer;
import com.disnodeteam.dogecv.scoring.PerfectAreaScorer;
import com.disnodeteam.dogecv.scoring.RatioScorer;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.disnodeteam.dogecv.detectors.DogeCVDetector;
import com.disnodeteam.dogecv.filters.DogeCVColorFilter;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.disnodeteam.dogecv.scoring.MaxAreaScorer;
import com.disnodeteam.dogecv.scoring.PerfectAreaScorer;
import com.disnodeteam.dogecv.scoring.RatioScorer;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Victo on 9/10/2018.
 */

public class SamplingOrderDetector extends DogeCVDetector {

    // Enum to describe gold location
    public enum GoldLocation {
        UNKNOWN,
        LEFT,
        CENTER,
        RIGHT
    }

////////////////////////////////////////////////////////////////////////////////////

    // Results of the detector
    private boolean found1    = false; // Is the gold mineral found
    private boolean aligned1  = false; // Is the gold mineral aligned
    private double  goldXPos1 = 0;     // X Position (in pixels) of the gold element

    // Detector settings
    public boolean debugAlignment1 = true; // Show debug lines to show alignment settings
    public double alignPosOffset1  = 0;    // How far from center frame is aligned
    public double alignSize1       = 100;  // How wide is the margin of error for alignment


    // ///////////////////////////////////////////////////////////////////////////////
    // Which area scoring method to use
    public DogeCV.AreaScoringMethod areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;

    //Create the scorers used for the detector
    public RatioScorer ratioScorer             = new RatioScorer(1.0,5);
    public MaxAreaScorer maxAreaScorer         = new MaxAreaScorer(0.01);
    public PerfectAreaScorer perfectAreaScorer = new PerfectAreaScorer(5000,0.05);

    //Create the filters used
    public DogeCVColorFilter yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW,100);
    public DogeCVColorFilter whiteFilter  = new HSVRangeFilter(new Scalar(0,0,200), new Scalar(50,40,255));


    // Results for the detector
    private GoldLocation currentOrder = GoldLocation.UNKNOWN;
    private GoldLocation lastOrder    = GoldLocation.UNKNOWN;
    private boolean      isFound      = false;

    // Create the mats used
    private Mat maskYellow = new Mat(); // Yellow Mask returned by color filter
    private Mat workingMat  = new Mat();
    private Mat displayMat  = new Mat();
    private Mat yellowMask  = new Mat();
    private Mat whiteMask   = new Mat();
    private Mat hiarchy     = new Mat();

    public SamplingOrderDetector() {
        super();
        this.detectorName = "Sampling Order Detector";
    }

    @Override
    public Mat process(Mat input) {

        // Copy input mat to working/display mats
        input.copyTo(displayMat);
        input.copyTo(workingMat);
        input.release();

        // Generate Masks
        yellowFilter.process(workingMat.clone(), yellowMask);
        whiteFilter.process(workingMat.clone(), whiteMask);

        // Blur and find the countours in the masks
        List<MatOfPoint> contoursYellow = new ArrayList<>();
        List<MatOfPoint> contoursWhite = new ArrayList<>();

        Imgproc.blur(whiteMask,whiteMask,new Size(2,2));
        Imgproc.blur(yellowMask,yellowMask,new Size(2,2));

        Imgproc.findContours(yellowMask, contoursYellow, hiarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(displayMat,contoursYellow,-1,new Scalar(230,70,70),2);

        Imgproc.findContours(whiteMask, contoursWhite, hiarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(displayMat,contoursWhite,-1,new Scalar(230,70,70),2);


        // Prepare to find best yellow (gold) results
        Rect   chosenYellowRect  = null;
        double chosenYellowScore = Integer.MAX_VALUE;

        MatOfPoint2f approxCurve = new MatOfPoint2f();

        for(MatOfPoint c : contoursYellow){
            MatOfPoint2f contour2f = new MatOfPoint2f(c.toArray());

            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint(approxCurve.toArray());

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(points);

            double diffrenceScore = calculateScore(points);

            if(diffrenceScore < chosenYellowScore && diffrenceScore < maxDifference){
                chosenYellowScore = diffrenceScore;
                chosenYellowRect = rect;
            }

            double area = Imgproc.contourArea(c);
            double x = rect.x;
            double y = rect.y;
            double w = rect.width;
            double h = rect.height;
            Point centerPoint = new Point(x + ( w/2), y + (h/2));
            if( area > 500){
                Imgproc.circle(displayMat,centerPoint,3,new Scalar(0,255,255),3);
                Imgproc.putText(displayMat,"Area: " + area,centerPoint,0,0.5,new Scalar(0,255,255));
            }
        }

        // Prepare to find best white (silver) results
        List<Rect>   choosenWhiteRect  = new ArrayList<>(2);
        List<Double> chosenWhiteScore  = new ArrayList<>(2);
        chosenWhiteScore.add(0, Double.MAX_VALUE);
        chosenWhiteScore.add(1, Double.MAX_VALUE);
        choosenWhiteRect.add(0, null);
        choosenWhiteRect.add(1, null);


        for(MatOfPoint c : contoursWhite){
            MatOfPoint2f contour2f = new MatOfPoint2f(c.toArray());

            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint(approxCurve.toArray());

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(points);

            double diffrenceScore = calculateScore(points);

            double area = Imgproc.contourArea(c);
            double x = rect.x;
            double y = rect.y;
            double w = rect.width;
            double h = rect.height;
            Point centerPoint = new Point(x + ( w/2), y + (h/2));
            if( area > 1000){
                Imgproc.circle(displayMat,centerPoint,3,new Scalar(0,255,255),3);
                Imgproc.putText(displayMat,"Area: " + area,centerPoint,0,0.5,new Scalar(0,255,255));
                Imgproc.putText(displayMat,"Diff: " + diffrenceScore,new Point(centerPoint.x, centerPoint.y + 20),0,0.5,new Scalar(0,255,255));
            }

            boolean good = true;
            if(diffrenceScore < maxDifference && area > 1000){

                if(diffrenceScore < chosenWhiteScore.get(0)){
                    choosenWhiteRect.set(0,rect);
                    chosenWhiteScore.set(0,diffrenceScore);
                }
                else if(diffrenceScore < chosenWhiteScore.get(1) && diffrenceScore > chosenWhiteScore.get(0)){
                    choosenWhiteRect.set(1,rect);
                    chosenWhiteScore.set(1, diffrenceScore);
                }
            }


        }

        //Draw found gold element
        if(chosenYellowRect != null){
            Imgproc.rectangle(displayMat,
                    new Point(chosenYellowRect.x, chosenYellowRect.y),
                    new Point(chosenYellowRect.x + chosenYellowRect.width, chosenYellowRect.y + chosenYellowRect.height),
                    new Scalar(255, 0, 0), 2);

            Imgproc.putText(displayMat,
                    "Gold: " + String.format("%.2f X=%.2f", chosenYellowScore, (double)chosenYellowRect.x),
                    new Point(chosenYellowRect.x - 5, chosenYellowRect.y - 10),
                    Core.FONT_HERSHEY_PLAIN,
                    1.3,
                    new Scalar(0, 255, 255),
                    2);

        }
        //Draw found white elements
        for(int i=0;i<choosenWhiteRect.size();i++){
            Rect rect = choosenWhiteRect.get(i);
            if(rect != null){
                double score = chosenWhiteScore.get(i);
                Imgproc.rectangle(displayMat,
                        new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 255, 255), 2);
                Imgproc.putText(displayMat,
                        "Silver: " + String.format("Score %.2f ", score) ,
                        new Point(rect.x - 5, rect.y - 10),
                        Core.FONT_HERSHEY_PLAIN,
                        1.3,
                        new Scalar(255, 255, 255),
                        2);
            }


        }

        // If enough elements are found, compute gold position
        if(choosenWhiteRect.get(0) != null && choosenWhiteRect.get(1) != null  && chosenYellowRect != null){
            int leftCount = 0;
            for(int i=0;i<choosenWhiteRect.size();i++){
                Rect rect = choosenWhiteRect.get(i);
                if(chosenYellowRect.x > rect.x){
                    leftCount++;
                }
            }
            if(leftCount == 0){
                currentOrder = SamplingOrderDetector.GoldLocation.LEFT;
            }

            if(leftCount == 1){
                currentOrder = SamplingOrderDetector.GoldLocation.CENTER;
            }

            if(leftCount >= 2){
                currentOrder = SamplingOrderDetector.GoldLocation.RIGHT;
            }
            isFound = true;
            lastOrder = currentOrder;

        }else{
            currentOrder = SamplingOrderDetector.GoldLocation.UNKNOWN;
            isFound = false;
        }

        //Display Debug Information
        Imgproc.putText(displayMat,"Gold Position: " + lastOrder.toString(),new Point(10,getAdjustedSize().height - 30),0,1, new Scalar(255,255,0),1);
        Imgproc.putText(displayMat,"Current Track: " + currentOrder.toString(),new Point(10,getAdjustedSize().height - 10),0,0.5, new Scalar(255,255,255),1);

        //return displayMat;

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Copy the input mat to our working mats, then release it for memory

        // Copy the input mat to our working mats, then release it for memory
        //input.copyTo(displayMat);
        //input.copyTo(workingMat);
        //input.release();


        //Preprocess the working Mat (blur it then apply a yellow filter)
        Imgproc.GaussianBlur(workingMat,workingMat,new Size(5,5),0);
        yellowFilter.process(workingMat.clone(),maskYellow);

        //Find contours of the yellow mask and draw them to the display mat for viewing

        ArrayList<MatOfPoint> contoursYellow1 = new ArrayList<>();
        Imgproc.findContours(maskYellow, contoursYellow1, hiarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(displayMat,contoursYellow1,-1,new Scalar(230,70,70),2);

        // Current result
        Rect bestRect1 = null;
        double bestDiffrence1 = Double.MAX_VALUE; // MAX_VALUE since less diffrence = better

        // Loop through the contours and score them, searching for the best result
        for(MatOfPoint cont : contoursYellow1){
            double score = calculateScore(cont); // Get the diffrence score using the scoring API

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(cont);
            Imgproc.rectangle(displayMat, rect.tl(), rect.br(), new Scalar(0,0,255),2); // Draw rect

            // If the result is better then the previously tracked one, set this rect as the new best
            if(score < bestDiffrence1){
                bestDiffrence1 = score;
                bestRect1 = rect;
            }
        }

        // Vars to calculate the alignment logic.
        double alignX    = (getAdjustedSize().width / 2) + alignPosOffset1; // Center point in X Pixels
        double alignXMin = alignX - (alignSize1 / 2); // Min X Pos in pixels
        double alignXMax = alignX +(alignSize1 / 2); // Max X pos in pixels
        double xPos; // Current Gold X Pos

        if(bestRect1 != null){
            // Show chosen result
            Imgproc.rectangle(displayMat, bestRect1.tl(), bestRect1.br(), new Scalar(255,0,0),4);
            Imgproc.putText(displayMat, "Chosen", bestRect1.tl(),0,1,new Scalar(255,255,255));

            // Set align X pos
            xPos = bestRect1.x + (bestRect1.width / 2);
            goldXPos1 = xPos;

            // Draw center point
            Imgproc.circle(displayMat, new Point( xPos, bestRect1.y + (bestRect1.height / 2)), 5, new Scalar(0,255,0),2);

            // Check if the mineral is aligned
            if(xPos < alignXMax && xPos > alignXMin){
                aligned1 = true;
            }else{
                aligned1 = false;
            }

            // Draw Current X
            Imgproc.putText(displayMat,"Current X: " + bestRect1.x,new Point(10,getAdjustedSize().height - 50),0,0.5, new Scalar(255,255,255),1);
            found1 = true;
        }else{
            found1 = false;
            aligned1 = false;
        }
        if(debugAlignment1){

            //Draw debug alignment info
            if(isFound()){
                Imgproc.line(displayMat,new Point(goldXPos1, getAdjustedSize().height), new Point(goldXPos1, getAdjustedSize().height - 30),new Scalar(255,255,0), 2);
            }

            Imgproc.line(displayMat,new Point(alignXMin, getAdjustedSize().height), new Point(alignXMin, getAdjustedSize().height - 40),new Scalar(0,255,0), 2);
            Imgproc.line(displayMat,new Point(alignXMax, getAdjustedSize().height), new Point(alignXMax,getAdjustedSize().height - 40),new Scalar(0,255,0), 2);
        }

        //Print result
        Imgproc.putText(displayMat,"Result: " + aligned1,new Point(10,getAdjustedSize().height - 80),0,1, new Scalar(255,255,0),1);


        return displayMat;
    }

    @Override
    public void useDefaults() {
        if(areaScoringMethod == DogeCV.AreaScoringMethod.MAX_AREA){
            addScorer(maxAreaScorer);
        }

        if (areaScoringMethod == DogeCV.AreaScoringMethod.PERFECT_AREA){
            addScorer(perfectAreaScorer);
        }
        addScorer(ratioScorer);
    }
    /**
     * Set the alignment settings for GoldAlign
     * @param offset - How far from center frame (in pixels)
     * @param width - How wide the margin is (in pixels, on each side of offset)
     */
    public void setAlignSettings(int offset, int width){
        alignPosOffset1 = offset;
        alignSize1 = width;
    }

    /**
     * Returns if the gold element is aligned
     * @return if the gold element is alined
     */
    public boolean getAligned(){
        return aligned1;
    }

    /**
     * Returns gold element last x-position
     * @return last x-position in screen pixels of gold element
     */
    public double getXPosition(){
        return goldXPos1;
    }

    /**
     * Returns if a gold mineral is being tracked/detected
     * @return if a gold mineral is being tracked/detected
     */
    public boolean isFound() {
        return found1;
    }

    /**
     * Is both elements found?
     * @return if the elements are found
     */

    /**
     * Returns the current gold pos
     * @return current gold pos (UNKNOWN, LEFT, CENTER, RIGHT)
     */
    public GoldLocation getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Returns the last known gold pos
     * @return last known gold pos (UNKNOWN, LEFT, CENTER, RIGHT)
     */
    public GoldLocation getLastOrder() {
        return lastOrder;
    }
}
