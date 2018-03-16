/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.mundane.retrofitanalysis.api;

// @formatter:off

import me.mundane.retrofitanalysis.model.MeizhiModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by drakeet on 8/9/15.
 */
public interface GankApi {

    // http://gank.io/api/data/福利/10/1
    @GET("api/data/福利/{pagesize}/{page}")
    Call<MeizhiModel> getMeiZhi(@Path("pagesize") int pageSize, @Path("page") int page);

}
