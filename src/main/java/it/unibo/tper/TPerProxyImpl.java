/*******************************************************************************
 * Participact
 * Copyright 2013-2018 Alma Mater Studiorum - Università di Bologna
 * 
 * This file is part of ParticipAct.
 * 
 * ParticipAct is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * ParticipAct is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with ParticipAct. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package it.unibo.tper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

import it.unibo.tper.ws.domain.OpenDataFermate;
import it.unibo.tper.ws.domain.OpenDataFermateResponse;
import it.unibo.tper.ws.domain.OpenDataLineeFermate;
import it.unibo.tper.ws.domain.OpenDataLineeFermateResponse;
import it.unibo.tper.ws.domain.extensions.FermateResponse;



public class TPerProxyImpl extends WebServiceGatewaySupport implements TPerProxy{


	@Override
	public FermateResponse getOpenDataLineeFermateResponse() {
		OpenDataLineeFermate request = new OpenDataLineeFermate();
		OpenDataLineeFermateResponse response =  (OpenDataLineeFermateResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback(	"https://solweb.tper.it/tperit/webservices/opendata.asmx/OpenDataLineeFermate"));

		try {
			JAXBContext payloadContext = JAXBContext.newInstance(FermateResponse.class);			
			FermateResponse fermateResponse = (FermateResponse) payloadContext.createUnmarshaller().unmarshal(((ElementNSImpl) response.getOpenDataLineeFermateResult().getAny()).getFirstChild());
			return fermateResponse;	
		} catch (JAXBException e) {			
			return null;
		}


	}
}
