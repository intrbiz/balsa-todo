package com.intrbiz.balsa.demo.todo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.intrbiz.balsa.engine.route.Router;
import com.intrbiz.metadata.Any;
import com.intrbiz.metadata.Prefix;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Timer;

@Prefix("/")
public class MetricsUI extends Router
{
    @Any("/metrics")
    public void metrics()
    {
        List<MetricDisplay> metrics = model("metrics", new LinkedList<MetricDisplay>());
        for (Entry<String, SortedMap<MetricName, Metric>> group : Metrics.defaultRegistry().groupedMetrics().entrySet())
        {
            for (Entry<MetricName, Metric> metric : group.getValue().entrySet())
            {
                if (metric.getValue() instanceof Timer)
                {
                    metrics.add(new MetricDisplay(group.getKey(), metric.getKey().getName(), metric.getKey().getScope(), metric.getValue()));
                }
            }
        }
        encode("metrics");
    }

    public static class MetricDisplay
    {
        private final String group;

        private final String name;

        private final String scope;

        private final Metric metric;

        public MetricDisplay(String group, String name, String scope, Metric metric)
        {
            this.group = group;
            this.name = name;
            this.scope = scope;
            this.metric = metric;
        }

        public String getGroup()
        {
            return group;
        }

        public String getScope()
        {
            return scope;
        }

        public String getName()
        {
            return name;
        }

        public Metric getMetric()
        {
            return metric;
        }
    }
}
