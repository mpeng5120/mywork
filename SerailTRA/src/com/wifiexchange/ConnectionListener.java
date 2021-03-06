/**    Copyright 2012 Brayden (headdetect) Lopez
 *    
 *    Dual-licensed under the Educational Community License, Version 2.0 and
 *	the GNU General Public License Version 3 (the "Licenses"); you may
 *	not use this file except in compliance with the Licenses. You may
 *	obtain a copy of the Licenses at
 *
 *		http://www.opensource.org/licenses/ecl2.php
 *		http://www.gnu.org/licenses/gpl-3.0.html
 *
 *		Unless required by applicable law or agreed to in writing
 *	software distributed under the Licenses are distributed on an "AS IS"
 *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *	or implied. See the Licenses for the specific language governing
 *	permissions and limitations under the Licenses.
 * 
 */
package com.wifiexchange;






/**
 * The listener interface for receiving connection events.
 * The class that is interested in processing a connection
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>setOnConnectionListener<code> method. When
 * the connection event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ConnectionEvent
 */
public interface ConnectionListener {
	
	/**
	 * On disconnect.
	 *
	 * @param client the client
	 */
	void onDisconnect(Client client);
	
	/**
	 * On join.
	 *
	 * @param client the client
	 */
	void onJoin(Client client);

}


