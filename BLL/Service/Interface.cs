using DAL.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BLL.Service
{
    public interface IProductService
    {
        Task<IEnumerable<Product>> GetAllAsync();
        Task<Product> GetByIdAsync(object id);
        Task AddAsync(Product item);
        Task UpdateAsync(Product item);
        Task DeleteAsync(object id);

        Task<Product> GetByNameAsync(object name);

    }

}
