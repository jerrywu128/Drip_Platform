package com.example.drip_platform;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import com.example.drip_platform.electrocardiogram;

public class draw_elec_test {
    private Timer timer;
    private TimerTask timerTask;
    /**
     * 模拟源源不断的数据源
     */
    public void showWaveData(final electrocardiogram elec){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                elec.showLine(new Random().nextFloat()*(30f)-20f);
            }
        };
        //500表示调用schedule方法后等待500ms后调用run方法，50表示以后调用run方法的时间间隔
        timer.schedule(timerTask,500,50);
    }

    /**
     * 停止绘制波形
     */
    public void stop(){
        if(timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if(null != timerTask) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}
