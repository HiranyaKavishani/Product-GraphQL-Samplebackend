/*
 *
 *  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package test.product.graphql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import org.apache.cxf.helpers.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Path("/graphql")
public class GraphQLAPI {

	@POST
    @Consumes("application/json")
	@Produces("application/json")
	public Response getResults(JSONObject query) {
		String value = query.get("query").toString();
		List<Product> ProductCList = new ArrayList<>();
		List<Customer> customer = new ArrayList<>();
		List<AllProduct> allProductArrayList = new ArrayList<>();
		String jsonObj = "";
		JSONParser jsonParser = new JSONParser();

		if(value.contains("product") && value.contains("customer")) {
			try {
				String data = IOUtils.toString(
						getClass().getClassLoader().getResourceAsStream("ProductUsageByCustomer.json"),
						"UTF-8");
				JSONObject obj = (JSONObject) jsonParser.parse(data);
				JSONObject p1 = (JSONObject) obj.get("data");
				JSONObject linkObj2 =(JSONObject) p1.get("product");
				JSONArray ProductArray = (JSONArray) linkObj2.get("customer");
				for (Object p3 : ProductArray) {
					JSONObject linkObj3 = (JSONObject) p3;
					Customer cus =  new Customer(linkObj3.get("id").toString(), linkObj3.get("name").toString());
					customer.add(cus);
				}
				Product pro = new Product(linkObj2.get("id").toString(), linkObj2.get("name").toString(), customer);


				JSONObject responseProductDetailsJson = new JSONObject();
				JSONObject responseDetailsJson = new JSONObject();
				responseProductDetailsJson.put("product", pro);
				responseDetailsJson.put("data",responseProductDetailsJson);
				jsonObj = new Gson().toJson(responseDetailsJson);

			} catch (IOException | ParseException ignored) {

			}
		} else if (value.contains("allProducts")) try {
			String data = IOUtils.toString(
					getClass().getClassLoader().getResourceAsStream("allProduct.json"),
					"UTF-8");
			JSONObject obj = (JSONObject) jsonParser.parse(data);
			JSONObject productData = (JSONObject) obj.get("data");
			JSONArray productList = (JSONArray) productData.get("allProducts");
			for (Object productArray : productList) {
				JSONObject linkObj = (JSONObject) productArray;
				AllProduct allProduct = new AllProduct(linkObj.get("id").toString(), linkObj.get("name").toString(),
						linkObj.get("category").toString(), linkObj.get("description").toString());
				allProductArrayList.add(allProduct);
			}
			JSONObject responseDetailsJson = new JSONObject();
			responseDetailsJson.put("data", allProductArrayList);
			jsonObj = new Gson().toJson(responseDetailsJson);
		} catch (IOException | ParseException ignored) {

		}
		else if (value.contains("addProduct")) {
			try {
				String data = IOUtils.toString(
						getClass().getClassLoader().getResourceAsStream("addProduct.json"),
						"UTF-8");
				jsonObj = new Gson().toJson(jsonParser.parse(data));
			}
			catch (IOException | ParseException ignored) {
			}

		} else {
			return Response.ok().entity("Wrong Query").build();
		}

		return Response.ok().entity(jsonObj).build();
	}
}
