package frc.robot.lib.util;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RevThroughBoreEncoder {
    private DutyCycleEncoder m_dutyCycleEncoder;
    private Rotation2d m_offset = Rotation2d.fromDegrees(0);
    private boolean m_Inverted = false;

    public RevThroughBoreEncoder(int dioChannel){
        m_dutyCycleEncoder = new DutyCycleEncoder(dioChannel);
        m_dutyCycleEncoder.setDutyCycleRange(1.0/1024.0, 1023.0/1024.0);
        m_dutyCycleEncoder.setDistancePerRotation(360);
    }

    public Rotation2d getOffset() {
        return m_offset;
    }

    public void setOffset(Rotation2d m_offset) {
        this.m_offset = m_offset;
    }

    public boolean isInverted(){
        return m_Inverted;
    }

    public void setInverted(boolean inverted){
        m_Inverted = inverted;
    }

    public double getAngle(){
        double angle = m_dutyCycleEncoder.getDistance() % 360;
          
        angle -= m_offset.getDegrees();
     
        if (m_Inverted){
            angle = 360-angle;
        } 

        if (angle < 0.0){
            angle = angle + 360;
        }

        return Rotation2d.fromDegrees(angle % 360).getDegrees();

    }
    
}
