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
package ch.citux.td.data.model;

public class Response<Result> {

    private Status status;
    private Result result;

    public Response(Status status, Result result) {
        this.status = status;
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "status = " + status + " | result = " + result;
    }

    public enum Status {
        OK,
        ERROR_URL,
        ERROR_CONNECTION,
        ERROR_CONTENT
    }
}
