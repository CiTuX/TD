/*
 * Copyright 2013-2014 Paul Stöhr
 *
 * This file is part of TD.
 *
 * TD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.citux.td.data.service;

import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Follows;
import ch.citux.td.data.model.SearchChannels;
import ch.citux.td.data.model.SearchStreams;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.StreamToken;
import ch.citux.td.data.model.VideoPlaylist;
import ch.citux.td.data.model.Videos;

public interface TDService {

    public Follows getFollows(String username);

    public Channel getChannel(String channel);

    public Stream getStream(String channel);

    public Videos getVideos(String channel);

    public VideoPlaylist getVideoPlaylist(String id);

    public StreamToken getStreamToken(String channel);

    public StreamPlayList getStreamPlaylist(String channel, StreamToken streamToken);

    public SearchStreams searchStreams(String query, String offset);

    public SearchChannels searchChannels(String query, String offset);
}
