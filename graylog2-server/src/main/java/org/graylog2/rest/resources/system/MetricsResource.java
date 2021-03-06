/**
 * Copyright 2013 Lennart Koopmann <lennart@torch.sh>
 *
 * This file is part of Graylog2.
 *
 * Graylog2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog2.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.graylog2.rest.resources.system;

import com.codahale.metrics.Metric;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Maps;
import org.graylog2.rest.resources.RestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author Lennart Koopmann <lennart@torch.sh>
 */
@Path("/system/metrics")
public class MetricsResource extends RestResource {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsResource.class);

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public String metrics() {
        Map<String, Object> result = Maps.newHashMap();

        result.put("metrics", core.metrics().getMetrics());

        return json(result);
    }

    @GET
    @Timed
    @Path("/names")
    @Produces(MediaType.APPLICATION_JSON)
    public String metricNames() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("names", core.metrics().getNames());

        return json(result);
    }

    @GET
    @Timed
    @Path("/{metricName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String singleMetric(@PathParam("metricName") String metricName) {
        Metric metric = core.metrics().getMetrics().get(metricName);

        if (metric == null) {
            LOG.warn("I do not have a metric called [{}], returning 404.", metricName);
            throw new WebApplicationException(404);
        }

        return json(metric);
    }

}
