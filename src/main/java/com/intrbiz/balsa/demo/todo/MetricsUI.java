package com.intrbiz.balsa.demo.todo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.Timer;
import com.intrbiz.balsa.engine.route.Router;
import com.intrbiz.gerald.source.IntelligenceSource;
import com.intrbiz.gerald.witchcraft.Witchcraft;
import com.intrbiz.metadata.Any;
import com.intrbiz.metadata.Prefix;

@Prefix("/")
public class MetricsUI extends Router<App>
{
    @Any("/metrics")
    public void metrics()
    {
        List<MetricDisplay> metrics = model("metrics", new LinkedList<MetricDisplay>());
        for (IntelligenceSource source : Witchcraft.get().getSources())
        {
            for (Entry<String, Metric> metric : source.getRegistry().getMetrics().entrySet())
            {
                metrics.add(new MetricDisplay(source.getName(), metric.getKey(), metric.getValue()));
            }
        }
        encode("metrics");
    }

    public static class MetricDisplay
    {
        private final String source;
        
        private final String name;

        private final Metric metric;

        public MetricDisplay(String source, String name, Metric metric)
        {
            this.source = source;
            this.name = name;
            this.metric = metric;
        }
        
        public String getSource()
        {
            return source;
        }

        public String getName()
        {
            return name;
        }

        public Metric getMetric()
        {
            return metric;
        }
        
        public String getType()
        {
            return this.metric.getClass().getSimpleName();
        }
        
        public boolean isTimer()
        {
            return this.metric instanceof Timer;
        }
        
        public boolean isCounter()
        {
            return this.metric instanceof Counter;
        }
        
        public boolean isGauge()
        {
            return this.metric instanceof Gauge;
        }
        
        public boolean isMeter()
        {
            return this.metric instanceof Meter;
        }
        
        public boolean isHistogram()
        {
            return this.metric instanceof Histogram;
        }
    }
}
