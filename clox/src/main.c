#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Node
{
   struct Node *next;
   struct Node *last;
   char *value;
};

struct Node *head = NULL;
struct Node *tail = NULL;

// inserts new node at beginning
void insert(char value[])
{
   // create new node
   struct Node *newNode = (struct Node *)malloc(sizeof(struct Node));

   // set newNode's value and links
   newNode->value = value;
   newNode->next = head;
   newNode->last = NULL;
   if (head != NULL)
      head->last = newNode;

   // set beginning to the new node
   head = newNode;
}

// deletes beginning node
void deleteFirst()
{
   head = head->next;
}

void deleteNode(struct Node *toDelete)
{
   toDelete->last->next = toDelete->next;
   toDelete->next->last = toDelete->last;

   free(toDelete);
}

struct Node *popOff()
{
   struct Node *temp = head;

   deleteFirst();

   return temp;
}

struct Node *find(char valueToFind[])
{
   for (struct Node *i = head; i != tail; i = i->next)
   {
      if (strcmp(i->value, valueToFind) == 0)
      {
         return i;
      }
      else
      {
         continue;
      }
   }

   return NULL;
}

void printList()
{
   for (struct Node *i = head; i != tail; i = i->next)
   {
      printf("%s\n", i->value);
   }
   printf("\n");
}

int main()
{
   insert("First");
   insert("Second");
   insert("Third");
   insert("Red");
   insert("Blue");

   printList();

   deleteNode(find("Third"));

   printList();

   printf("%s\n\n", popOff()->value);

   printList();

   return 0;
}