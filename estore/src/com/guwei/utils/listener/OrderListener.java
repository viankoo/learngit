package com.guwei.utils.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.guwei.service.OrderService;
import com.guwei.utils.ImplFactory;

public class OrderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent eve) {
	}

	@Override
	public void contextInitialized(ServletContextEvent eve) {
		final OrderService os = ImplFactory.getInstance(OrderService.class);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			public void run() {
				System.out.println("---開始掃描訂單---");
				os.canceltimeoutOrder();
			}
		}, 10000, 1000 * 60 * 3); // 延迟10s 循环周期3min
	}

}
