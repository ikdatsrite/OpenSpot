//
//  DrivewayViewController.swift
//  OpenSpot
//
//  Created by Stephen Fung on 4/12/19.
//  Copyright © 2019 Jay Lliguichushca. All rights reserved.
//

import UIKit
import MapKit
import Firebase

class DrivewayViewController : UIViewController {
    @IBOutlet weak var searchResultTableView: UITableView!
    @IBOutlet weak var addressSearchBar: UISearchBar!
    @IBOutlet weak var addressTextView: UITextField!
    @IBOutlet weak var priceLabel: UILabel!
    @IBOutlet weak var availablitySegmentedControl: UISegmentedControl!
    @IBOutlet weak var priceSlide: UISlider!
    @IBAction func priceSlider(_ sender: UISlider) {
        priceLabel.text = "$" + String(format: "%.2f", sender.value.rounded()) + "/hr"
    }
    @IBAction func finishClicked(_ sender: Any) {
        if addressTextView.text == ""{
            showErrorMessage(message: "Enter your driveway address")
        }
        else{
            if addressIndex == nil{
            let db = Firestore.firestore()
            let currentUser = Auth.auth().currentUser
            db.collection("Users").document((currentUser?.uid)!).getDocument {(value, Error) in
                let getAddressesArray = value!["Addresses"] as? [String]
                self.addressesArray = [self.addressTextView.text!, self.lat, self.long, String(format: "%.2f", self.priceSlide.value.rounded()), String(self.availablitySegmentedControl.selectedSegmentIndex)]
                db.collection("Users").document((currentUser?.uid)!).updateData([
                    "Addresses": getAddressesArray! + self.addressesArray])
                let destinationVC = self.storyboard?.instantiateViewController(withIdentifier: "DrivewayListViewController") as! DrivewayListViewController
                self.show(destinationVC, sender: nil)
            }
            }else{
                let db = Firestore.firestore()
                let currentUser = Auth.auth().currentUser
                db.collection("Users").document((currentUser?.uid)!).getDocument {(value, Error) in
                    var getAddressesArray = value!["Addresses"] as? [String]
                    getAddressesArray![self.addressIndex!] = self.addressTextView.text!
                    getAddressesArray![self.addressIndex!+1] = self.lat
                    getAddressesArray![self.addressIndex!+2] = self.long
                    getAddressesArray![self.addressIndex!+3] = String(format: "%.2f", self.priceSlide.value.rounded())
                    getAddressesArray![self.addressIndex!+4] = String(self.availablitySegmentedControl.selectedSegmentIndex)
                    db.collection("Users").document((currentUser?.uid)!).updateData([
                        "Addresses": getAddressesArray!])
                    let destinationVC = self.storyboard?.instantiateViewController(withIdentifier: "DrivewayListViewController") as! DrivewayListViewController
                    self.show(destinationVC, sender: nil)
                }
            }
        }
    }
    
    var completerResults: [MKLocalSearchCompletion] = []
    let searchCompleter = MKLocalSearchCompleter()
    var addressIndex: Int?
    var addressesArray:[String] = []
    var lat = ""
    var long = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.searchCompleter.delegate = self
        addressTextView.underlined()
        fillInformation()
    }
    
    func fillInformation (){
        if addressIndex != nil{
            let db = Firestore.firestore()
            let currentUser = Auth.auth().currentUser
            let user = db.collection("Users").document((currentUser?.uid)!)
            var addressArray = [String]()
            user.getDocument { (value, Error) in
                addressArray = (value!["Addresses"] as? [String])!
                self.addressTextView.text = addressArray[self.addressIndex!]
                self.lat = addressArray[self.addressIndex!+1]
                self.long = addressArray[self.addressIndex!+2]
                self.priceLabel.text = "$" + addressArray[self.addressIndex!+3] + "/hr"
                self.priceSlide.setValue(Float(addressArray[self.addressIndex!+3])!, animated: false)
            }
        }
        }

    
    func showErrorMessage(message : String) {
        let alertController = UIAlertController(title: "", message: message, preferredStyle: UIAlertController.Style.alert)
        alertController.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default, handler: nil))
        self.present(alertController, animated: true, completion: nil)
    }
}

extension DrivewayViewController: UITableViewDelegate{
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
}

extension DrivewayViewController: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.completerResults.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: nil)
        cell.textLabel?.text = self.completerResults[indexPath.row].title
        cell.detailTextLabel?.text = self.completerResults[indexPath.row].subtitle
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let completion = completerResults[indexPath.row]
        addressTextView.text = String(completion.title)
        searchResultTableView.isHidden = true
        addressSearchBar.isHidden = true
        addressSearchBar.text = ""
        let searchRequest = MKLocalSearch.Request(completion: completion)
        let search = MKLocalSearch(request: searchRequest)
        search.start { (response, error) in
            let coordinate = response?.mapItems[0].placemark.coordinate
            self.lat = coordinate?.latitude.description ?? "0"
            self.long = coordinate?.longitude.description ?? "0"
            print(String(describing: coordinate))
            self.view.endEditing(true)
        }
    }
}

extension DrivewayViewController: MKLocalSearchCompleterDelegate {
    func completerDidUpdateResults(_ completer: MKLocalSearchCompleter) {
        self.completerResults = completer.results
        self.searchResultTableView.reloadData()
    }
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchResultTableView.isHidden = true
        addressSearchBar.isHidden = true
    }
}

extension DrivewayViewController: UISearchBarDelegate {
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        searchCompleter.queryFragment = searchText
    }
}

extension DrivewayViewController: UITextFieldDelegate{
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        addressSearchBar.isHidden = false
        searchResultTableView.isHidden = false
        DispatchQueue.main.async {
            self.addressSearchBar.becomeFirstResponder()
        }
        print("exampleTextView: START EDIT")
        return true
    }
    func textFieldShouldEndEditing(_ textField: UITextField) -> Bool {
        print("exampleTextView: END EDIT")
        return true
    }
}
