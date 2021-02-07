//============================================================================
// Name        : BinarySearchTree.cpp
// Author      : Nick Parente
// Version     : 2.0
// Copyright   : Copyright © 2017 SNHU COCE
// Description : Binary Search Tree Application for Bidding system
//============================================================================

// The included files for the I/O for the user input, the algorithm functions, the clock for ticks, and the CSV parser
#include <iostream>
#include <algorithm>
#include <time.h>
#include "CSVparser.hpp"

// Defines the std namespace for I/O to avoid having to constantly type std::
using namespace std;

//============================================================================
// Global definitions visible to all methods and classes
//============================================================================

// Forward declaration for converting string to double
double strToDouble(string str, char ch);

// Defines a structure to hold bid information
struct Bid {
    string bidId; // unique identifier
    string title;
    string fund;
    double amount;
    Bid() {
        amount = 0.0;
    }
};

// Defines a structure for the node 
struct Node {
    Bid bid;
    Node* left;
    Node* right; 
    Node() {
        left = nullptr;
        right = nullptr;
    }
    Node(Bid aBid) : Node() {
    
        this->bid = aBid;
    }
};

//============================================================================
// Binary Search Tree class definition
//============================================================================

/**
 * Defines a class containing data members and methods to
 * implement a binary search tree
 */
class BinarySearchTree {

private:
    Node* root;

    void addNode(Node* node, Bid bid);
    void inOrder(Node* node);
    void inOrderTill(Node* node, string Id);
    Node* removeNode(Node* node, string bidId);

public:
    BinarySearchTree();
    virtual ~BinarySearchTree();
    void InOrder();
    void InOrderTill(string bidKey);
    void Insert(Bid bid);
    void Remove(string bidId);
    Bid Search(string bidId);
};

/**
 * Default constructor
 */
BinarySearchTree::BinarySearchTree() {
    // initialize housekeeping variables

    root = nullptr;
}

/**
 * Destructor
 */
BinarySearchTree::~BinarySearchTree() {
    // recurse from root deleting every node
}

/**
 * Traverse the tree in order
 */
void BinarySearchTree::InOrder() {
    this->inOrder(root);

}

/**
 * Traverse the tree from the bottom left to the defined key
 */
void BinarySearchTree::InOrderTill(string bidKey) {
    this->inOrderTill(root, bidKey);

}
/**
 * Insert a bid
 */
void BinarySearchTree::Insert(Bid bid) {

    if (root == nullptr) {
        root = new Node(bid);
    }
    else {
        this->addNode(root, bid);
    }
}

/**
 * Remove a bid
 */
void BinarySearchTree::Remove(string bidId) {

        this->removeNode(root, bidId);
}

/**
 * Search for a bid
 */
Bid BinarySearchTree::Search(string bidId) {
    
    // Start searching from the root
    Node* current = root;

    // Keep looking down the tree until the bottom is reached or the bid is found
    while (current != nullptr) {
        // If the current node matches, return it
        if (current->bid.bidId.compare(bidId) == 0) {
            return current->bid;
        }
        // If the current node is smaller, search the left side of the tree
        if (bidId.compare(current->bid.bidId) < 0) {
            current = current->left;
        }
        // If the current node is larger, search the right side of the tree
        else {
        
            current = current->right;
        }
    }

	Bid bid;
    return bid;
}

/**
 * Add a bid to some node
 *
 */
void BinarySearchTree::addNode(Node* node, Bid bid) {

    // If the node is larger than the bid, add to the left subtree
    if (node->bid.bidId.compare(bid.bidId) > 0) {
        if (node->left == nullptr) {
            node->left = new Node(bid);
        }
        else {
            this->addNode(node->left, bid);
        }
    }

    // Add to the right subtree
    else {
        if (node->right == nullptr) {
            node->right = new Node(bid);
        }
        else {
            this->addNode(node->right, bid);
        }
        
    }
}

/**
 * Remove a bid from the list of bids
 *
 */
Node* BinarySearchTree::removeNode(Node* node, string bidId) {
    // If the node is null then return to avoid a crash
    if (node == nullptr) {
        return node;
    }

    // Traverse through the left side of the tree
    if (bidId.compare(node->bid.bidId) < 0) {
        node->left = removeNode(node->left, bidId);
    }
    // Traverse through the right side of the tree
    else if(bidId.compare(node->bid.bidId) > 0){
        node->right = removeNode(node->right, bidId);
    }
    // If the node is found
    else {
        // If the node has no children
        if (node->left == nullptr && node->right == nullptr) {
            delete node;
            node = nullptr;
            cout << endl << "I.D. " << bidId << " has been deleted." << endl << endl;
        }
        // If the node has a child to the left
        else if (node->left != nullptr && node->right == nullptr) {
            Node* temp = node;
            node = node->left;
            delete temp;
            cout << endl << "I.D. " << bidId << " has been deleted." << endl << endl;

        }
        // If the node has a child to the right
        else if (node->right != nullptr && node->left == nullptr) {
            Node* temp = node;
            node = node->right;
            delete temp;
            cout << endl << "I.D. " << bidId << " has been deleted." << endl << endl;

        }
        // If the node has 2 children 
        else {
            Node* temp = node->right;
            while (temp->left != nullptr) {
                temp = temp->left;
            }
            node->bid = temp->bid;
            node->right = removeNode(node->right, bidId);
            cout << endl << "I.D. " << bidId << " has been deleted." << endl << endl;
        }

    }

    return node;
}

/**
 * Prints the bids in order 
 *
 */
void BinarySearchTree::inOrder(Node* node) {
    
    // Recursively traverses through the tree till null is reached
    // Prints from one end of the tree to the other recursively
    if (node != nullptr) {
        inOrder(node->left);
        cout << node->bid.bidId 
            << ": " << node->bid.title 
            << " | " 
            << node->bid.amount 
            << " | "
            << node->bid.fund 
            << endl;
        inOrder(node->right);

    }
}

/**
 * Prints the bids from the far left to the defined value by the user
 *
 */
void BinarySearchTree::inOrderTill(Node* node, string Id) {

    // Recursively traverses through the left side of the tree till null 
    if (node != nullptr) {
        inOrderTill(node->left, Id);

        // If the bid is larger than the bid requested by the user
        if (node->bid.bidId.compare(Id) > 0) {
            return;
        }

        // If the bid is less than or equal to the bid requested
        // Print the bid and traverse right
        else {
            cout << node->bid.bidId
                << ": " << node->bid.title
                << " | "
                << node->bid.amount
                << " | "
                << node->bid.fund
                << endl;
            inOrderTill(node->right, Id);
        }

    }
}

/**
 * Display the bid information to the console (std::out)
 *
 */
void displayBid(Bid bid) {
    cout << endl << bid.bidId << ": " << bid.title << " | " << bid.amount << " | "
            << bid.fund << endl << endl;
    return;
}

/**
 * Load a CSV file containing bids into a container
 *
 * Returns a container holding all the bids
 */
void loadBids(string csvPath, BinarySearchTree* bst) {
    cout << endl << "Loading CSV file " << csvPath << endl << endl;

    // Initialize the CSV Parser using the given path
    csv::Parser file = csv::Parser(csvPath);

    // Read and display header row - optional
    vector<string> header = file.getHeader();

    try {
        // Loop to read rows of a CSV file
        for (unsigned int i = 0; i < file.rowCount(); i++) {

            // Create a data structure and add to the collection of bids
            Bid bid;
            bid.bidId = file[i][1];
            bid.title = file[i][0];
            bid.fund = file[i][8];
            bid.amount = strToDouble(file[i][4], '$');

            // Push this bid to the end
            bst->Insert(bid);
        }
    } catch (csv::Error &e) {
        std::cerr << e.what() << std::endl;
    }
}

/**
 * Simple C function to convert a string to a double
 * after stripping out unwanted char
 *
 * credit: http://stackoverflow.com/a/24875936
 *
 */
double strToDouble(string str, char ch) {
    str.erase(remove(str.begin(), str.end(), ch), str.end());
    return atof(str.c_str());
}

/**
 * The main method of the application
 */
int main() {

    // UPDATE: removed command line arguments to make program less seceptible to malicious interference

    // The string for the bid to be searched for
    string bidKey;

    // The CSV file path for the bids. Can be changed to other CSV file in folder if more bids are needed
    string csvPath = "eBid_Monthly_Sales_Dec_2016.csv";

    // Define a timer variable
    clock_t ticks;

    // Define a binary search tree to hold all bids
    BinarySearchTree* bst;

    // Defines the bid data structure
    Bid bid;

    // Initializes the binary search tree object
    bst = new BinarySearchTree();

    // The choice variable for the menu input
    int choice = 0;

    // The switch used to determine if the user loaded the bids before using the application
    int switcher = 0;

    // Displays the main menu to the user and accepts the user input
    while (choice != 9) {
        cout << "Menu:" << endl;
        cout << "  1. Load Bids" << endl;
        cout << "  2. Display All Bids" << endl;
        cout << "  3. Find Bid" << endl;
        cout << "  4. Remove Bid" << endl;
        cout << "  5. Display Bids up to a Key" << endl;
        cout << "  9. Exit" << endl;
        cout << "Enter choice: ";
            cin >> choice;

        switch (choice) {

        case 1:

            // Initialize a timer variable before loading bids
            ticks = clock();

            // Complete the method call to load the bids
            loadBids(csvPath, bst);

            // Calculate elapsed time and display result
            ticks = clock() - ticks; // current clock ticks minus starting clock ticks
            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl << endl;

            // Sets the switch signifying the bids have been loaded
            switcher = 1;
            break;

        case 2:

            // If the user did not load the bids, break
            if (switcher == 0) {
                cout << endl << "Please import the bids into the application. " << endl << endl;
                break;
            }

            // Prints the bids in order 
            bst->InOrder();
            break;

        case 3:

            // If the user did not load the bids, break
            if (switcher == 0) {
                cout << endl << "Please import the bids into the application. " << endl << endl;
                break;
            }

            // Retrieves the I.D. of the bid to search for 
            cout << endl << "Enter the bid I.D. you would like to search for:";

            cin >> bidKey;

            // Starts the clock
            ticks = clock();

            // Searches for the bids
            bid = bst->Search(bidKey);

            // Sets the time it took to complete the task
            ticks = clock() - ticks;

            // If the bid is not empty, print the bids
            if (!bid.bidId.empty()) {
                displayBid(bid);
            } 
            
            // If the bid is empty, print message to the user
            else {
            	cout << endl << "Bid Id " << bidKey << " not found." << endl << endl;
            }

            // Print to the user the amount of time taken to complete task
            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl << endl;

            break;

        case 4:

            // If the user did not load the bids, break
            if (switcher == 0) {
                cout << endl << "Please import the bids into the application. " << endl << endl;
                break;
            }

            // Retrieves the I.D. of the bid to remove
            cout << endl << "Enter the bid I.D. you would like to remove:";

            cin >> bidKey;

            // Searches for the bids
            bid = bst->Search(bidKey);

            // Starts the clock
            ticks = clock();

            // If the bid exists, remove it
            if (!bid.bidId.empty()) {

                bst->Remove(bidKey);

            }

            // If the bid does not exist, print message to user
            else {
                cout << endl << "Bid Id " << bidKey << " not found." << endl << endl;
            }

            ticks = clock() - ticks; // current clock ticks minus starting clock ticks

            // Prints out the time it took to the user
            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl << endl;

            break;

        case 5:

            // If the user did not load the bids, break
            if (switcher == 0) {
                cout << endl << "Please import the bids into the application. " << endl << endl;
                break;
            }

            // Retrieves the I.D. of the bid to print up to
            cout << endl << "Enter the bid I.D. to display up to:";

            cin >> bidKey;

            // Searches for the bids
            bid = bst->Search(bidKey);

            // Starts the clock
            ticks = clock();

            // If the bid exists, print up to it
            if (!bid.bidId.empty()) {

                bst->InOrderTill(bidKey);

            }

            // If the bid does not exist print message to user
            else {
                cout << endl << "Bid Id " << bidKey << " not found." << endl << endl;
            }

            
            ticks = clock() - ticks; // current clock ticks minus starting clock ticks

            // Prints out the time it took to the user
            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl << endl;

            break;

        case 9:
            break;

        //Default in switch-case added to provide error handling if a user did not enter the correct value
        default:

            //Prints the error message to the user, spacing using \n for new menu output
            cout << endl << "Incorrect choice. Choice must be 1,2,3,4,5, or 9. Please enter a number." << endl << endl;

            //clears the cin value so the user can enter data again
            cin.clear();

            //will ignore up to 10000 characters entered by the user and goes to the next newline
            cin.ignore(10000, '\n');

        }
    }

    cout << endl << "Goodbye!" << endl;

	return 0;
}
