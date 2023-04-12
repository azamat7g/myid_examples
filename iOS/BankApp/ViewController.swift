//
//  ViewController.swift
//  BankApp
//
//  Created by Azamat on 07/04/21.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var authTypeSwitch: UISwitch!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func login(_ sender: Any) {
        var urlComps = URLComponents(string: "https://myid.uz/api/v1/oauth2/authorization")!
        let queryItems = [
            URLQueryItem(name: "client_id", value: YOU_CLIENT_ID),
            URLQueryItem(name: "redirect_uri", value: "uzinfocom://bank"),
            URLQueryItem(name: "response_type", value: "code"),
            URLQueryItem(name: "method", value: authTypeSwitch.isOn ? "strong" : "simple"),
            URLQueryItem(name: "scope", value: "common_data,doc_data,contacts,address"),
        ]
        
        urlComps.queryItems = queryItems
        
        UIApplication.shared.open(urlComps.url!)
    }
}

