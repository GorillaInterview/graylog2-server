/**
 * Copyright 2012 Lennart Koopmann <lennart@socketfeed.com>
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
package org.graylog2.alarms.transports;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.graylog2.Core;
import org.graylog2.plugin.alarms.transports.Transport;

/**
 * @author Lennart Koopmann <lennart@socketfeed.com>
 */
public class TransportRegistry {
    
    public static void setActiveTransports(Core server, List<Transport> transports) {
        Set<Map<String, String>> r = Sets.newHashSet();
        
        for(Transport transport : transports) {
            Map<String, String> entry = Maps.newHashMap();
            entry.put("typeclass", transport.getClass().getCanonicalName());
            entry.put("name", transport.getName());
            
            r.add(entry);
        }
        
        server.getMongoBridge().writeTransports(r);
    }
    
}
